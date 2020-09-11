package com.problem.pl.controller

import com.problem.pl.commons.RequestMappingCommon
import com.problem.pl.commons.ResultCommon
import com.problem.pl.commons.UniversalCommon
import com.problem.pl.commons.VerifyCode
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TUserEntity
import com.problem.pl.model.services.UserService
import eu.bitwalker.useragentutils.UserAgent
import org.springframework.web.bind.annotation.*
import java.io.ByteArrayOutputStream
import java.util.*
import javax.annotation.Resource
import javax.imageio.ImageIO
import javax.servlet.http.HttpServletRequest

/**
 * 用户类控制器
 */
@RequestMapping(RequestMappingCommon.MAPPING_USER)
@RestController
class UserController {

    @Resource(name = "userService")
    lateinit var userService: UserService

    /**
     * 获取验证码
     */
    @RequestMapping(value = [RequestMappingCommon.MAPPING_USER_GET_VERIFY_CODE], produces = ["image/jpg"])
    fun getVerifyCode(): ByteArray {
        val outputStream = ByteArrayOutputStream()
        ImageIO.write(VerifyCode.image, "jpg", outputStream)
        return outputStream.toByteArray()
    }

    /**
     * 注册
     */
    @RequestMapping(RequestMappingCommon.MAPPING_USER_REGISTER)
    fun register(@RequestParam(name = "account", required = true) account: String,
                 @RequestParam(name = "pwd", required = false) pwd: String,
                 @RequestParam(name = "nickname", required = false) nickname: String,
                 request: HttpServletRequest): ResultPro<TUserEntity> =
            userService.registerVerificationAndSave(account, pwd,nickname,request.getHeader("User-Agent"))


    /**
     * 登录
     */
    @RequestMapping(RequestMappingCommon.MAPPING_USER_LOGIN)
    fun login(@RequestParam(name = "account", required = true) account: String,
              @RequestParam(name = "pwd", required = false) pwd: String,
              request: HttpServletRequest): ResultPro<TUserEntity> =
            userService.login(account, pwd, request.getHeader("User-Agent"))
}

fun main() {
    val pagingCalculation = UniversalCommon.pagingCalculation(2, 30, 500)
    print(pagingCalculation)
}