package com.problem.pl.model.dao

import com.problem.pl.model.entities.TProjectProblemEntity

/**
 * 项目管理Mapper
 */
interface ProjectProblemMapper {

    fun insertBatchProjectProblems(tppes: MutableList<TProjectProblemEntity>): Int

    /**
     * 根据项目id查询 问题列表
     */
    fun queryProjectProblemsListByProjectId(projectId: String,startPos: Int,endPos: Int): MutableList<TProjectProblemEntity>

    /**
     * 查询某个项目下问题总数量
     */
    fun getPPListByProjectIdCount(projectId: String): Int
}