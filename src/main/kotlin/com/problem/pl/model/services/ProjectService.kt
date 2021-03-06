package com.problem.pl.model.services

import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectEntity
import com.problem.pl.model.entities.TProjectOperateRecorderEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestParam

@Service
interface ProjectService {

    fun queryProjectById(projectId: String): ResultPro<TProjectEntity>

    fun queryPListByPagination(page: Int,pageCount: Int): ResultPro<TProjectEntity>

    fun queryProjectListByUserId(userId: String, page: Int, pageCountSize: Int): ResultPro<TProjectEntity>

    fun saveNewProjectInfo(projectName: String, projectLevel: Int,projectDesc: String,uid: String): ResultPro<String>

    fun modifyProjectPriority(priority: Int,projectId: String): ResultPro<String>

    fun modifyProjectName(projectName: String,projectId: String): ResultPro<String>

    fun deleteProjectById(projectId: String): ResultPro<String>


    /**
     * 查询问题页面的顶部项目标签，
     * 分页查询
     */
    fun queryProjectToLabelsByPagination(curPage: Int,pageSize: Int): ResultPro<TProjectEntity>

    /**
     * 查询问题页面推荐的项目标签
     * 最近修改的问题所属的项目
     */
    fun queryRecommendProjectLabelsForProblem(userId: String,labelNum: Int): ResultPro<TProjectEntity>


    /**
     * 根据项目id查询此项目操作记录
     */
    fun queryProjectOperateRecordsByProjectId(projectId: String,curPage: Int,pageSize: Int): ResultPro<TProjectOperateRecorderEntity>


}