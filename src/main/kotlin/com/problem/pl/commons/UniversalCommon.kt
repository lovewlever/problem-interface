package com.problem.pl.commons

import com.problem.pl.model.entities.PageE
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.math.abs

object UniversalCommon {

    fun generateDBId(): String = UUID.randomUUID().toString().replace("-","")

    fun generateTimestamp() : Long = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli()

    /**
     * 计算分页
     * [curPage] 要计算的当前页
     * [dataTotalCount] 要计算的总数据
     * [pageCountSize] 要查询的每页的数量
     */
    fun pagingCalculation(curPage: Int,pageCountSize: Int,dataTotalCount: Int): PageE {
        //总页数
        val pageSize = Math.ceil(dataTotalCount.toDouble() / pageCountSize.toDouble()).toInt()
        //LIMIT 起始位置
        val startPos = (curPage - 1) * pageCountSize
        //LIMIT 结束位置
        val endPos = startPos + pageCountSize
        return PageE().apply {
            this.curPage = curPage
            this.dataTotalCount = dataTotalCount
            this.totalPage = pageSize
            this.startPos = startPos
            this.endPos = endPos
        }
    }
}