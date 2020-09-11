package com.problem.pl.model.entities

data class ResultPro<T> (var code:Int = -1,
                         var msg:String = "",
                         var pagination: PageE = PageE(),
                         var data:MutableList<T> = ArrayList())