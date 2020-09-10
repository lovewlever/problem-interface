package com.self.problem.probleminterface.model.entitices

data class ResultPro<T> (var code:Int = -1,
                         var curPage:Int = 1,
                         var tolPage:Int = 1,
                         var msg:String = "",
                         var data:MutableList<T> = ArrayList())