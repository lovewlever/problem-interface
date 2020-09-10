package com.problem.pl.commons

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Type

object GsonCommon {

    val gson: Gson by lazy { Gson() }

    fun registerTypeAdapterOfGson(type: Type, typeAdapter :Any): Gson {
        return GsonBuilder().apply {
            registerTypeAdapter(type,typeAdapter)
        }.create()
    }

}