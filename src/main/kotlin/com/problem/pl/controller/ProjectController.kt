package com.problem.pl.controller

import com.problem.pl.commons.RequestMappingCommon
import com.problem.pl.commons.ResultCommon
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectEntity
import com.problem.pl.model.services.ProjectService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest

/**
 * 项目管理控制器
 */
@RequestMapping(RequestMappingCommon.MAPPING_PC)
@RestController
class ProjectController {

    @Resource(name = "projectService")
    lateinit var projectService: ProjectService

    /**
     * 根据id查询项目详情
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PC_QUERY_PROJECT_BY_ID)
    fun queryProjectById(@RequestParam("projectId") projectId: String): ResultPro<TProjectEntity> =
            projectService.queryProjectById(projectId)

    /**
     * 查询项目列表
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PC_PROJECT_LIST)
    fun queryProjectList(@RequestParam("page") page: Int,
                         @RequestParam("pageCountSize") pageCountSize: Int): ResultPro<TProjectEntity> =
            projectService.queryPListByPagination(page,pageCountSize)

    /**
     * 添加一个项目
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PC_ADD_NEW_PROJECT)
    fun addNewProject(@RequestParam("projectName") projectName: String,
                      @RequestParam("projectLevel") projectLevel: Int,
                      @RequestParam("projectDesc") projectDesc: String,
                      request: HttpServletRequest): ResultPro<String> =
            projectService.saveNewProjectInfo(projectName, projectLevel,projectDesc,request.getAttribute("uid").toString())

    /**
     * 修改项目的优先级
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PC_MODIFY_PROJECT_PRIORITY)
    fun modifyProjectPriority(@RequestParam("priority") priority: Int,
                              @RequestParam("projectId") projectId: String): ResultPro<String> =
            projectService.modifyProjectPriority(priority,projectId)

    /**
     * 修改项目的名称
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PC_MODIFY_PROJECT_NAME)
    fun modifyProjectName(@RequestParam("projectName") projectName: String,
                          @RequestParam("projectId") projectId: String): ResultPro<String> =
            projectService.modifyProjectName(projectName,projectId)

    /**
     * 通过id删除一个项目
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PC_DELETE_PROJECT_BY_ID)
    fun deleteProjectById(@RequestParam("projectId") projectId: String): ResultPro<String> =
            projectService.deleteProjectById(projectId)
}