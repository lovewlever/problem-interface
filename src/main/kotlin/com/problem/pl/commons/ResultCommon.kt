package com.problem.pl.commons

import com.problem.pl.model.entities.PageE
import com.problem.pl.model.entities.ResultPro

object ResultCommon {

    const val RESULT_CODE_SUCCESS = 200 //成功
    const val RESULT_CODE_NOT_LOGIN = 4089 //未登录
    const val RESULT_CODE_TOKEN_NOT_EMPTY = 4092 //token不能为空
    const val RESULT_CODE_ALREADY_REGISTER = 4084 //已经注册
    const val RESULT_CODE_NOT_REGISTER = 4085 //未注册
    const val RESULT_CODE_REGISTER_FAIL = 4083 //注册失败
    const val RESULT_CODE_FAIL = 400 //失败

    fun <T> generateResult(code: Int = RESULT_CODE_SUCCESS,
                           msg: String = "SUCCESS",
                           pagination: PageE = PageE()): ResultPro<T> =
            getResult(code, msg, pagination)

    fun <T> generateResult(code: Int = RESULT_CODE_SUCCESS,
                           msg: String = "SUCCESS",
                           pagination: PageE = PageE(),
                           data: T? = null): ResultPro<T> {
        return data?.let {
            getResult(code, msg, pagination, ArrayList<T>().apply { add(data) })
        } ?: getResult(code, msg, pagination)
    }

    fun <T> generateResult(code: Int = RESULT_CODE_SUCCESS,
                           msg: String = "SUCCESS",
                           pagination: PageE = PageE(),
                           data: MutableList<T>? = null): ResultPro<T> {
        return data?.let {
            getResult(code, msg, pagination, data)
        } ?: getResult(code, msg, pagination)
    }


    private fun <T> getResult(code: Int = -1,
                              msg: String = "",
                              pagination: PageE = PageE(),
                              data: MutableList<T> = ArrayList()): ResultPro<T> =
            ResultPro(code, msg, pagination, data)
}