package com.problem.pl.commons

import com.problem.pl.model.entities.ResultPro

object ResultCommon {

    const val RESULT_CODE_SUCCESS = 200
    const val RESULT_CODE_NOT_LOGIN = 4089
    const val RESULT_CODE_TOKEN_NOT_EMPTY = 4092
    const val RESULT_CODE_ALREADY_REGISTER = 4084
    const val RESULT_CODE_REGISTER_FAIL = 4083


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