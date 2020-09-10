package com.problem.pl.controller

import com.problem.pl.commons.RequestMappingCommon
import com.problem.pl.commons.ResultCommon
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TUserEntity
import com.problem.pl.model.services.UserService
import eu.bitwalker.useragentutils.UserAgent
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest

@RequestMapping(RequestMappingCommon.MAPPING_USER)
@RestController
class UserController {

    @Resource(name = "userService")
    lateinit var userService: UserService

    /**
     * 注册
     */
    @RequestMapping(RequestMappingCommon.MAPPING_USER_REGISTER)
    fun register(@RequestParam(name = "account",required = true) account: String,
                 @RequestParam(name = "pwd",required = false) pwd: String,
                 @RequestParam(name = "verificationCode",required = false ,value = "") verificationCode: String,
                 request: HttpServletRequest): ResultPro<TUserEntity> {
        return userService.registerVerificationAndSave(account, pwd, verificationCode,request.getHeader("User-Agent"))
    }

    /**
     * 登录
     */
    @RequestMapping(RequestMappingCommon.MAPPING_USER_LOGIN)
    fun login(): ResultPro<TUserEntity> = ResultCommon.generateResult()
}

fun main() {
    print(UUID.randomUUID().toString().replace("-",""))
}