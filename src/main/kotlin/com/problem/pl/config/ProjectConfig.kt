package com.problem.pl.config

import com.problem.pl.commons.RequestMappingCommon
import com.problem.pl.interceptor.LoginVerificationInterceptor
import com.problem.pl.websocket.ProblemInterfaceWebsocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurationSupport
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@EnableWebSocket
@Configuration
class ProjectConfig: WebMvcConfigurer, WebSocketConfigurer{

    override fun addCorsMappings(registry: CorsRegistry) {
        //registry.addMapping("/**")
                //.allowedOrigins("*")
                //.allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                //.allowedHeaders("x-requested-with,content-type,token,auth","token")
                //.allowCredentials(true)
                //.maxAge(3600)
                //.allowedHeaders("*")
        super.addCorsMappings(registry)
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LoginVerificationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("${RequestMappingCommon.MAPPING_USER}/**")

        super.addInterceptors(registry)
    }

    /**
     * 注册WebSocket
     */
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(ProblemInterfaceWebsocketHandler(),"/ws/websocket.server")
                .addHandler(ProblemInterfaceWebsocketHandler(),"/ws/websocketJs.server").withSockJS()
    }


}