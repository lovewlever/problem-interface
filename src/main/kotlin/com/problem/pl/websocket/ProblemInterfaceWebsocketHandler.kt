package com.problem.pl.websocket

import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap

/**
 * WebSocket
 */
class ProblemInterfaceWebsocketHandler:TextWebSocketHandler() {

    private val onlineUsers = ConcurrentHashMap<String, WebSocketSession>()


    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        super.afterConnectionClosed(session, status)
        onlineUsers.remove(session.id)
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        super.afterConnectionEstablished(session)
        onlineUsers[session.id] = session
        session.uri?.query?.split("=")?.let {  lst ->
            if (lst.size > 1) {

            } else {
                //参数错误关闭
                session.close(CloseStatus(400))
            }
        } ?: session.close(CloseStatus(400))

    }


    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        super.handleMessage(session, message)
        println("handleMessage:${message.payload}")
        session.sendMessage(message)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        super.handleTextMessage(session, message)
        println("handleTextMessage")
    }
}