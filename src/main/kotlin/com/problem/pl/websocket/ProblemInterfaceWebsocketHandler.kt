package com.problem.pl.websocket

import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

/**
 * WebSocket
 */
class ProblemInterfaceWebsocketHandler:TextWebSocketHandler() {

    private val onlineUsers = HashMap<String, WebSocketSession>()


    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        super.afterConnectionClosed(session, status)
        onlineUsers.remove(session.id)
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        super.afterConnectionEstablished(session)
        onlineUsers[session.id] = session
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