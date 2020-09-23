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
import javax.websocket.server.PathParam

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
    fun getVerifyCode(@RequestParam("sessionKey") sessionKey: String,request: HttpServletRequest): ByteArray {
        val outputStream = ByteArrayOutputStream()
        val verifyCode = VerifyCode()
        ImageIO.write(verifyCode.image, "jpg", outputStream)
        (request.session ?: request.getSession(true)).let { ss ->
            ss.maxInactiveInterval = 2 * 60
            ss.setAttribute(sessionKey,verifyCode.value)
        }
        return outputStream.toByteArray()
    }

    /**
     * 注册
     */
    @RequestMapping(RequestMappingCommon.MAPPING_USER_REGISTER,method = [RequestMethod.POST])
    fun register(@RequestParam("account") account: String,
                 @RequestParam("pwd") pwd: String,
                 @RequestParam("nickname") nickname: String,
                 @RequestParam("sessionKey") sessionKey: String,
                 @RequestParam("verifyCode") verifyCode: Int,
                 request: HttpServletRequest): ResultPro<TUserEntity>  {
        var code: Any? = null
        request.session?.let { ss ->
            code = ss.getAttribute(sessionKey)
            ss.removeAttribute(sessionKey)
        }
        return if (code == verifyCode) {
            userService.registerVerificationAndSave(account, pwd,nickname,request.getHeader("User-Agent"))
        } else {
            ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "验证码错误！")
        }
    }



    /**
     * 登录
     */
    @RequestMapping(RequestMappingCommon.MAPPING_USER_LOGIN)
    fun login(@RequestParam(name = "account", required = true) account: String,
              @RequestParam(name = "pwd", required = false) pwd: String,
              @RequestParam("sessionKey") sessionKey: String,
              @RequestParam("verifyCode") verifyCode: Int,
              request: HttpServletRequest): ResultPro<TUserEntity> {
        var code: Any? = null
        request.session?.let { ss ->
            code = ss.getAttribute(sessionKey)
            ss.removeAttribute(sessionKey)
        }
        return if (code == verifyCode) {
            userService.login(account, pwd, request.getHeader("User-Agent"))
        } else {
            ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "验证码错误！")
        }
    }

}

fun main() {
    val pagingCalculation = UniversalCommon.pagingCalculation(2, 30, 500)
    print("65:F7:B1:0F:1E:D9:C3:8B:A4:F9:AC:E4:95:DA:25:EC".replace(":","").toLowerCase())

}
