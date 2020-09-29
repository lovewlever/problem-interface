package com.problem.pl.controller

import com.problem.pl.commons.HttpConnectCommon
import com.problem.pl.commons.RequestMappingCommon
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectInterfaceEntity
import com.problem.pl.model.services.ProjectInterfaceService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest

/**
 * 项目接口Controller
 */
@RequestMapping(RequestMappingCommon.MAPPING_PIF)
@RestController
class ProjectInterfaceController {

    @Resource
    lateinit var projectInterfaceService: ProjectInterfaceService

    /**
     * 新增一个接口
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PIF_SAVE_A_INTERFACE)
    fun saveProjectInterface(@RequestParam("projectId") projectId: String,
                             @RequestParam("jsonData") json: String,
                                request: HttpServletRequest): ResultPro<TProjectInterfaceEntity> {
        return projectInterfaceService.saveProjectInterface(projectId,
                request.getAttribute(RequestMappingCommon.REQUEST_ATTRIBUTE_KEY_USER_ID).toString(),json)
    }

    /**
     * 分页查询项目下的接口
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PIF_QUERY_INTERFACES_BY_PROJECT_ID)
    fun queryProjectInterfacesByProjectId(@RequestParam("projectId") projectId: String,
                                          @RequestParam("page") page: Int,
                                          @RequestParam("pageCountSize") pageCountSize: Int): ResultPro<TProjectInterfaceEntity> =
        projectInterfaceService.queryProjectInterfacesByProjectId(projectId,page,pageCountSize)

    /**
     * 查询接口详情
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PIF_QUERY_INTERFACE_BY_ID)
    fun queryProjectInterfaceById(@RequestParam("interfaceId") interfaceId: String): ResultPro<TProjectInterfaceEntity> =
            projectInterfaceService.queryProjectInterfaceById(interfaceId)

    /**
     * 请求接口数据并返回到客户端
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PIF_REQUEST_INTERFACE)
    fun requestInterfaceAndReturn(@RequestParam("url") url: String): String {
        return HttpConnectCommon.interfaceRequest(url)
    }
}