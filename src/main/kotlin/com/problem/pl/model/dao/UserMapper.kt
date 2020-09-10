package com.problem.pl.model.dao

import com.problem.pl.model.entities.TUserEntity

interface UserMapper {

    fun loadUserByUId(uid: String): TUserEntity?

    fun loadUserByAccount(account: String): TUserEntity?

    fun isAlreadyRegisteredByAccount(account: String): Boolean

    fun saveRegisterInfo(tUserEntity: TUserEntity): Int

}