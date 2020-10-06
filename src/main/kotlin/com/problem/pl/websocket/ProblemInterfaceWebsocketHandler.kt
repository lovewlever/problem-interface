package com.problem.pl.websocket

import org.slf4j.LoggerFactory
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * WebSocket
 */
class ProblemInterfaceWebsocketHandler: TextWebSocketHandler() {

    private val log = LoggerFactory.getLogger(ProblemInterfaceWebsocketHandler::class.java)

    companion object {

        private val onlineUsers = ConcurrentHashMap<String, WebSocketSession>()

        /**
         * 发送全局消息
         */
        fun sendGlobalMessage(msg: String) {
            onlineUsers.forEach { (t: String, u: WebSocketSession) ->
                u.sendMessage(TextMessage(msg))
            }
        }

        /**
         * 发送消息给某个用户
         */
        fun sendSingleMessageByUserId(userId: String, msg: String) {
            onlineUsers[userId]?.sendMessage(TextMessage(msg))
        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        super.afterConnectionClosed(session, status)
        log.debug("afterConnectionClosed:${session.uri?.query}")
        removeForMap(session)
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        super.afterConnectionEstablished(session)
        log.debug("afterConnectionEstablished:${session.uri?.query}")
        addSessionToMap(session)
    }


    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        super.handleMessage(session, message)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        super.handleTextMessage(session, message)
        if (message.payload == "HeartBeat") {
            log.debug("HeartBeat:${session.uri?.query}")
            session.sendMessage(TextMessage("HeartbeatSuccess"))
        }
    }


    private fun addSessionToMap(session: WebSocketSession) {
        session.uri?.query?.split("=")?.let {  lst ->
            log.debug("addSessionToMap:${lst}")
            if (lst.size > 1) {
                onlineUsers[lst[1]] = session
                log.debug("addSessionToMap:添加Session成功")
            } else {
                //参数错误关闭
                log.debug("addSessionToMap:参数错误关闭")
                session.close(CloseStatus(400))
            }
        } ?: let {
            log.debug("addSessionToMap:参数错误关闭")
            session.close(CloseStatus(400))
        }
    }

    private fun removeForMap(session: WebSocketSession) {
        session.uri?.query?.split("=")?.let {  lst ->
            log.debug("removeForMap:${lst}")
            if (lst.size > 1) {
                onlineUsers.remove(lst[1])
                log.debug("removeForMap:移除Session成功")
            }
        }
    }
}