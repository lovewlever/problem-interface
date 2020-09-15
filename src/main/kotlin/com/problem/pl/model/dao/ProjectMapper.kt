package com.problem.pl.model.dao

import com.problem.pl.model.entities.TProjectEntity

/**
 * 项目管理Mapper
 */
interface ProjectMapper {

    /**
     * 查询问题页面推荐的项目标签
     * 最近修改的问题所属的项目
     */
    fun queryRecommendProjectLabelsForProblem(userId: String,labelNum: Int): MutableList<TProjectEntity>

    fun queryProjectById(projectId: String): TProjectEntity?

    fun queryPListByPagination(startPos: Int,entPos: Int): MutableList<TProjectEntity>

    fun queryPListByUserIdAndPagination(userId: String,startPos: Int,entPos: Int): MutableList<TProjectEntity>

    fun saveNewProjectInfo(tProjectEntity: TProjectEntity): Int

    fun findProjectTotalCount(): Int

    fun findProjectTotalCountByUserId(userId: String): Int

    fun modifyProjectPriority(priority: Int,pid: String): Int

    fun modifyProjectName(projectName: String,projectId: String): Int

    fun deleteProjectById(projectId: String): Int
}