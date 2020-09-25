package com.problem.pl.controller

import com.problem.pl.commons.RequestMappingCommon
import com.problem.pl.commons.ResultCommon
import com.problem.pl.model.entities.ResultPro
import org.springframework.web.bind.annotation.RequestMapping
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

    @RequestMapping
    fun options(): ResultPro<String> =
            ResultCommon.generateResult()

}