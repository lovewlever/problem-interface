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
    fun queryProjectProblemsListByProjectId(projectId: String,startPos: Int,pageCountSize: Int): MutableList<TProjectProblemEntity>

    /**
     * 查询某个项目下问题总数量
     */
    fun getPPListByProjectIdCount(projectId: String): Int

    /**
     * 查询最新的指定条问题
     */
    fun queryNewProblems(pageCount: Int): MutableList<TProjectProblemEntity>

    /**
     * 根据id查询一个问题
     */
    fun queryProblemById(problemId: String): TProjectProblemEntity?

    /**
     * 选择问题修改
     */
    fun updateChooseProblem(problemEntity: TProjectProblemEntity): Int

    /**
     * 修改问题进度
     */
    fun updateModifyProblemProgress(problemEntity: TProjectProblemEntity): Int
}