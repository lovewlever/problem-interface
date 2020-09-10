package com.problem.pl.model.services.impl

import com.problem.pl.commons.AESSecretCommon
import com.problem.pl.commons.PatternCommon
import com.problem.pl.commons.ResultCommon
import com.problem.pl.commons.UniversalCommon
import com.problem.pl.model.dao.UserMapper
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TUserEntity
import com.problem.pl.model.services.UserService
import eu.bitwalker.useragentutils.UserAgent
import org.springframework.stereotype.Service
import java.util.regex.Pattern
import javax.annotation.Resource

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
    override fun registerVerificationAndSave(account: String, pwd: String, verificationCode: String, userAgentString: String?): ResultPro<TUserEntity> {
        return try {
            registerVerification(account, pwd, verificationCode, userAgentString)
        } catch (e: Exception) {
            ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_REGISTER_FAIL, msg = "${e.message}")
        }
    }

    /**
     * 登录
     */
    override fun login(account: String, pwd: String, verificationCode: String): ResultPro<TUserEntity> {
        return try {
            loginVerification(account, pwd, verificationCode)
        } catch (e: Exception) {
            ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_REGISTER_FAIL, msg = "${e.message}")
        }
    }

    @Throws
    private fun loginVerification(account: String, pwd: String, verificationCode: String): ResultPro<TUserEntity> {
        userMapper.loadUserByAccount(account)?.let { tUserEntity: TUserEntity ->

        } ?: return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_NOT_REGISTER, msg = "账号未注册！")

        return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_NOT_REGISTER, msg = "账号未注册！")
    }

    @Throws
    private fun registerVerification(account: String, pwd: String, verificationCode: String, userAgentString: String?): ResultPro<TUserEntity> {
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
                when {
                    PatternCommon.PATTERN_PHONE.matcher(account).matches() -> {
                        this.uLoginPhone = account
                    }
                    PatternCommon.PATTERN_EMAIL.matcher(account).matches() -> {
                        this.uLoginEmail = account
                    }
                    else -> {
                        this.uLoginAccount = account
                    }
                }
                this.uLoginPwd = AESSecretCommon.encryptToStr(pwd, AESSecretCommon.DATAKEY) ?: pwd
                this.uNickname = account
            })
            return if (saveRegisterInfo > 0) {
                ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_SUCCESS, msg = "注册成功")
            } else {
                ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_REGISTER_FAIL, msg = "注册失败")
            }
        }
    }
}