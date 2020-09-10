package com.problem.pl.model.services

import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TUserEntity
import org.springframework.stereotype.Service

@Service
interface UserService {

    fun loadUserByUId(uid: String): TUserEntity?

    fun registerVerificationAndSave(account: String,pwd: String,verificationCode: String,userAgentString: String?): ResultPro<TUserEntity>

    fun login(account: String,pwd: String,verificationCode: String): ResultPro<TUserEntity>
}