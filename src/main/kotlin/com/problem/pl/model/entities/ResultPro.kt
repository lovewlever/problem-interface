package com.problem.pl.model.entities

data class ResultPro<T> (var code:Int = -1,
                         var curPage:Int = 1,
                         var tolPage:Int = 1,
                         var msg:String = "",
                         var data:MutableList<T> = ArrayList())