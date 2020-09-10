package com.problem.pl.model.services.impl

import com.problem.pl.model.dao.UserMapper
import com.problem.pl.model.services.UserService
import org.springframework.stereotype.Service
import javax.annotation.Resource

@Service("userService")
class UserServiceImpl : UserService {

    @Resource
    lateinit var userMapper: UserMapper
}