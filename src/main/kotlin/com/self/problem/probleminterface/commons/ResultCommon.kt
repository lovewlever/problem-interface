package com.self.problem.probleminterface.commons

import com.self.problem.probleminterface.model.entitices.ResultPro

object ResultCommon {

    const val RESULT_CODE_SUCCESS = 200
    const val RESULT_CODE_NOT_LOGIN = 1089
    const val RESULT_CODE_TOKEN_NOT_EMPTY = 1092

    fun <T> generateResult(code:Int = RESULT_CODE_SUCCESS,
                            curPage:Int = 1,
                            tolPage:Int = 1,
                            msg:String = "",
                            data: T? = null): ResultPro<T> =
            getResult(code,curPage,tolPage, msg, data)


    private fun <T> getResult(code:Int = -1,
                              curPage:Int = 1,
                              tolPage:Int = 1,
                              msg:String = "",
                              data: T? = null): ResultPro<T> =
            ResultPro(code,curPage,tolPage,msg,ArrayList<T>().apply {
                data?.let { add(it) }
            })
}