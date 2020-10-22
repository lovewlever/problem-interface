package com.problem.pl.model.services.impl

import com.problem.pl.commons.*
import com.problem.pl.model.dao.UserMapper
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TUserEntity
import com.problem.pl.model.services.UserService
import eu.bitwalker.useragentutils.UserAgent
import org.springframework.stereotype.Service
import java.util.regex.Pattern
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest

/**
 * 用户操作 逻辑处理类
 */
@Service("userService")
class UserServiceImpl : UserService {

    @Resource
    lateinit var userMapper: UserMapper

    override fun loadUserByUId(uid: String): TUserEntity? {
        val loadUser = userMapper.loadUserByUId(uid)
        return loadUser
    }

    /**
     * 注册
     */
    override fun registerVerificationAndSave(account: String, pwd: String, nickname: String, request: HttpServletRequest): ResultPro<TUserEntity> {
        return try {
            registerVerification(account, pwd, nickname, request.getHeader("User-Agent"), UniversalCommon.getIpAddr(request))
        } catch (e: Exception) {
            ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_REGISTER_FAIL, msg = "${e.message}")
        }
    }

    /**
     * 登录
     */
    override fun login(account: String, pwd: String,request: HttpServletRequest): ResultPro<TUserEntity> {
        return try {
            loginVerification(account, pwd, request.getHeader("User-Agent"), UniversalCommon.getIpAddr(request))
        } catch (e: Exception) {
            ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_REGISTER_FAIL, msg = "${e.message}")
        }
    }

    @Throws
    private fun loginVerification(account: String, pwd: String,userAgentString: String,ip: String?): ResultPro<TUserEntity> {
        val loadUserByAccount = userMapper.loadUserByAccount(account)
        if (loadUserByAccount.isEmpty()) {
            return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_NOT_REGISTER, msg = "账号未注册！")
        }
        val tUserEntity = loadUserByAccount[0]
        if (AESSecretCommon.decryptToStr(tUserEntity.uLoginPwd,AESSecretCommon.DATAKEY) == pwd) {
            //登录成功
            val generateJWT = JwtCommon.generateJWT(tUserEntity.id, account, userAgentString)
            val userAgent = UserAgent.parseUserAgentString(userAgentString)
            tUserEntity.apply {
                this.token = generateJWT
                this.lastLoginDevices = userAgent?.operatingSystem?.name ?: ""
                this.lastLoginTimestamp = UniversalCommon.generateTimestamp()
                this.lastLoginIp = ip ?: ""
                this.lastLoginDevicesUserAgentString = userAgentString
            }
            //保存登录记录
            userMapper.updateLastLoginInfo(tUserEntity)
            return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_SUCCESS, msg = "登录成功！",data = tUserEntity)
        } else {
            return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_NOT_REGISTER, msg = "密码错误！")
        }
    }

    @Throws
    private fun registerVerification(account: String, pwd: String,nickname: String, userAgentString: String?,ip: String?): ResultPro<TUserEntity> {
        val alreadyRegisteredByAccount = userMapper.isAlreadyRegisteredByAccount(account)
        if (alreadyRegisteredByAccount) { //已经注册的话返回已注册
            return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_ALREADY_REGISTER, msg = "该账号已注册！")
        } else {
            val userAgent = UserAgent.parseUserAgentString(userAgentString)
            val saveRegisterInfo = userMapper.saveRegisterInfo(TUserEntity().apply {
                this.id = UniversalCommon.generateDBId()
                this.registerTimestamp = UniversalCommon.generateTimestamp()
                this.registerDevices = userAgent?.operatingSystem?.name ?: ""
                this.registerDevicesUserAgentString = userAgentString ?: ""
                this.registerIp = ip ?: ""
                this.uLoginAccount = account
                when {
                    PatternCommon.PATTERN_PHONE.matcher(account).matches() -> {
                        this.uBindPhone = account
                    }
                    PatternCommon.PATTERN_EMAIL.matcher(account).matches() -> {
                        this.uBindEmail = account
                    }
                    else -> {
                        this.uLoginAccount = account
                    }
                }
                this.uLoginPwd = AESSecretCommon.encryptToStr(pwd, AESSecretCommon.DATAKEY) ?: pwd
                this.uNickname = nickname
            })
            return if (saveRegisterInfo > 0) {
                ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_SUCCESS, msg = "注册成功")
            } else {
                ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_REGISTER_FAIL, msg = "注册失败")
            }
        }
    }
}