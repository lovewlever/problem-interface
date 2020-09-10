package com.problem.pl.model.dao

import com.problem.pl.model.entities.TUserEntity

interface UserMapper {

    fun loadUserByUId(uid: String): TUserEntity?
}