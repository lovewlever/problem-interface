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
                      request: HttpServletRequest): ResultPro<String> =
            projectService.saveNewProjectInfo(projectName, request.getAttribute("uid").toString())
}