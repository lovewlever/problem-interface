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

    /**
     * 查询问题页面的顶部项目标签，
     * 分页查询
     */
    fun queryProjectToLabelsByPagination(startPos: Int,pageCountSize: Int): MutableList<TProjectEntity>

    /**
     * 根据项目id查询单个项目
     */
    fun queryProjectById(projectId: String): TProjectEntity?

    /**
     * 分页查询项目列表
     */
    fun queryPListByPagination(startPos: Int,pageCountSize: Int): MutableList<TProjectEntity>

    /**
     * 分页查询我发布的项目列表
     */
    fun queryPListByUserIdAndPagination(userId: String,startPos: Int,pageCountSize: Int): MutableList<TProjectEntity>

    /**
     * 保存一个新项目
     */
    fun saveNewProjectInfo(tProjectEntity: TProjectEntity): Int

    /**
     * 查询项目总数
     */
    fun findProjectTotalCount(): Int

    /**
     * 查询个人发布的项目总数
     */
    fun findProjectTotalCountByUserId(userId: String): Int

    /**
     * 修改项目的优先级
     */
    fun modifyProjectPriority(priority: Int,pid: String): Int

    /**
     * 修改项目名称
     */
    fun modifyProjectName(projectName: String,projectId: String): Int

    /**
     * 通过项目id删除一个项目
     */
    fun deleteProjectById(projectId: String): Int
}