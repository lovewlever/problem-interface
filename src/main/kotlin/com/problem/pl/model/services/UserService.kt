package com.problem.pl.model.services

import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TUserEntity
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
interface UserService {

    fun loadUserByUId(uid: String): TUserEntity?

    fun registerVerificationAndSave(account: String,pwd: String,nickname: String, request: HttpServletRequest): ResultPro<TUserEntity>

    fun login(account: String,pwd: String,request: HttpServletRequest): ResultPro<TUserEntity>
}