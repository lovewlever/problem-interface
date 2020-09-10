package com.self.problem.probleminterface.controller

import com.self.problem.probleminterface.commons.RequestMappingCommon
import com.self.problem.probleminterface.commons.ResultCommon
import com.self.problem.probleminterface.model.entitices.ResultPro
import com.self.problem.probleminterface.model.entitices.UserInfo
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RequestMapping(RequestMappingCommon.MAPPING_USER)
@RestController
class UserController {

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