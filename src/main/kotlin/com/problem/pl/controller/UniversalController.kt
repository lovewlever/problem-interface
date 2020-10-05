package com.problem.pl.controller

import com.problem.pl.commons.RequestMappingCommon
import com.problem.pl.commons.ResultCommon
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.websocket.ProblemInterfaceWebsocketHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 通用的控制器
 * 不知道干啥用 先建一下
 */
@RequestMapping(RequestMappingCommon.MAPPING_UNIVERSAL)
@RestController
class UniversalController {

    /**
     * 验证token有效性
     * 无需特殊处理 如果token过期由拦截器[LoginVerificationInterceptor]返回
     */
    @RequestMapping(RequestMappingCommon.MAPPING_UNIVERSAL_VERIFY_TOKEN)
    fun verifyToken(): ResultPro<String> =
            ResultCommon.generateResult()

    @RequestMapping("/options")
    fun options(): ResultPro<String> =
            ResultCommon.generateResult()

    /**
     * 发送全局WebSocket消息
     */
    @RequestMapping(RequestMappingCommon.MAPPING_UNIVERSAL_SEND_GLOBAL_WS_MSG)
    fun sendGlobalWsMessage(@RequestParam("msg") msg: String): ResultPro<Int> {
        ProblemInterfaceWebsocketHandler.sendGlobalMessage(msg)
        return ResultCommon.generateResult()
    }

    /**
     * 发送WebSocket消息给个人
     */
    @RequestMapping(RequestMappingCommon.MAPPING_UNIVERSAL_SEND_WS_MSG_TO_UID)
    fun sendWsMessageToUid(@RequestParam("uid") uid: String,
                           @RequestParam("msg") msg: String): ResultPro<Int> {
        ProblemInterfaceWebsocketHandler.sendSingleMessageByUserId(uid,msg)
        return ResultCommon.generateResult()
    }

}