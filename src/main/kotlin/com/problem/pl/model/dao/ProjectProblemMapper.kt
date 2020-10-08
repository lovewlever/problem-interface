package com.problem.pl.model.dao

import com.problem.pl.model.entities.TProjectProblemEntity

/**
 * 项目管理问题Mapper
 */
interface ProjectProblemMapper {

    /**
     * 插入新问题
     */
    fun insertBatchProjectProblems(tppes: MutableList<TProjectProblemEntity>): Int

    /**
     * 根据项目id查询 问题列表
     */
    fun queryProjectProblemsListByProjectId(projectId: String,startPos: Int,pageCountSize: Int): MutableList<TProjectProblemEntity>

    /**
     * 查询我选中未修改完的问题
     */
    fun queryMineNotCompletedProblems(uid: String,startPos: Int,pageCountSize: Int): MutableList<TProjectProblemEntity>

    /**
     * 查询我选中未修改完的问题的总数量
     */
    fun getMineNotCompletedProblemsCount(uid: String): Int

    /**
     * 查询我选中已经修改完的问题
     */
    fun queryMineCompletedProblems(uid: String,startPos: Int,pageCountSize: Int): MutableList<TProjectProblemEntity>

    /**
     * 查询我选中已经修改完的问题的总数量
     */
    fun getMineCompletedProblemsCount(uid: String): Int

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
     * 取消选择一个问题
     */
    fun updateCancelChooseProblem(problemEntity: TProjectProblemEntity): Int

    /**
     * 修改问题进度
     */
    fun updateModifyProblemProgress(problemEntity: TProjectProblemEntity): Int

    /**
     * 编辑 修改某个问题
     */
    fun updateEditProblem(problemEntity: TProjectProblemEntity): Int

    /**
     * 查询要导出的问题
     */
    fun exportProblemToTxt(type: String, projectId: String): MutableList<TProjectProblemEntity>
}