package com.problem.pl.controller

import com.problem.pl.commons.RequestMappingCommon
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectEntity
import com.problem.pl.model.entities.TProjectSystemDevicesEntity
import com.problem.pl.model.services.ProjectProblemService
import com.problem.pl.model.services.ProjectService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest

/**
 * 项目问题记录控制器
 */
@RequestMapping(RequestMappingCommon.MAPPING_PPC)
@RestController
class ProjectProblemController {

    @Resource(name = "projectService")
    lateinit var projectService: ProjectService

    @Resource(name = "projectProblemService")
    lateinit var projectProblemService: ProjectProblemService

    /**
     * 查询问题页面推荐的项目标签
     * 最近修改的问题所属的项目
     * 根据最近修改的问题 查询推荐的项目Label
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PPC_RECOMMEND_PROJECT_LABEL_FOR_PROBLEM)
    fun queryRecommendProjectLabelsForProblem(@RequestParam("labelNum") labelNum: Int,
                                              request: HttpServletRequest): ResultPro<TProjectEntity> =
            projectService.queryRecommendProjectLabelsForProblem(request.getAttribute(RequestMappingCommon.REQUEST_ATTRIBUTE_KEY_USER_ID).toString(), labelNum)

    /**
     * 查询系统设备列表
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PPC_PROJECT_SYSTEM_DEVICES)
    fun querySystemDevicesList(): ResultPro<TProjectSystemDevicesEntity> =
            projectProblemService.querySystemDevicesList()
}