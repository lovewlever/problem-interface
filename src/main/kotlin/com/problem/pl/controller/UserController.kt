package com.problem.pl.controller

import com.problem.pl.commons.RequestMappingCommon
import com.problem.pl.commons.ResultCommon
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.UserInfo
import com.problem.pl.model.services.UserService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.annotation.Resource

@RequestMapping(RequestMappingCommon.MAPPING_USER)
@RestController
class UserController {

    @Resource(name = "userService")
    lateinit var userService: UserService

    /**
     * 注册
     */
    @RequestMapping(RequestMappingCommon.MAPPING_USER_REGISTER)
    fun register(): ResultPro<MutableList<UserInfo>> {
        return  ResultCommon.generateResult()
    }

    /**
     * 登录
     */
    @RequestMapping(RequestMappingCommon.MAPPING_USER_LOGIN)
    fun login(): ResultPro<MutableList<UserInfo>> = ResultCommon.generateResult()
}

fun main() {
    print(UUID.randomUUID().toString().replace("-",""))
}