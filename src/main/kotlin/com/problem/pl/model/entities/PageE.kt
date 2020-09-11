package com.problem.pl.model.entities

/**
 * 分页
 */
class PageE {
    var curPage: Int = 1
    var totalPage: Int = 1
    var dataTotalCount: Int = 1
    var startPos: Int = 1
    var endPos: Int = 1


    override fun toString(): String {
        return "PageE(curPage=$curPage, totalPage=$totalPage, dataTotalCount=$dataTotalCount, startPos=$startPos, endPos=$endPos)"
    }

}