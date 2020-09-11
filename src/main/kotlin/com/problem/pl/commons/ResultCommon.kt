package com.problem.pl.commons

import com.problem.pl.model.entities.ResultPro

object ResultCommon {

    const val RESULT_CODE_SUCCESS = 200 //成功
    const val RESULT_CODE_NOT_LOGIN = 4089 //未登录
    const val RESULT_CODE_TOKEN_NOT_EMPTY = 4092 //token不能为空
    const val RESULT_CODE_ALREADY_REGISTER = 4084 //已经注册
    const val RESULT_CODE_NOT_REGISTER = 4085 //未注册
    const val RESULT_CODE_REGISTER_FAIL = 4083 //注册失败


    fun <T> generateResult(code:Int = RESULT_CODE_SUCCESS,
                           curPage:Int = 1,
                           tolPage:Int = 1,
                           msg:String = "SUCCESS",
                           data: T? = null): ResultPro<T> =
            getResult(code, curPage, tolPage, msg, data)


    private fun <T> getResult(code:Int = -1,
                              curPage:Int = 1,
                              tolPage:Int = 1,
                              msg:String = "",
                              data: T? = null): ResultPro<T> =
            ResultPro(code, curPage, tolPage, msg, ArrayList<T>().apply {
                data?.let { add(it) }
            })
}