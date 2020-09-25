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
@RequestMapping("/pif")
@RestController
class ProjectInterfaceController {

    @Resource
    lateinit var projectInterfaceService: ProjectInterfaceService

    @RequestMapping("/saveInterface")
    fun saveProjectInterface(@RequestParam("projectId") projectId: String,
                             @RequestParam("projectId") json: String,
                                request: HttpServletRequest): ResultPro<TProjectInterfaceEntity> {
        return projectInterfaceService.saveProjectInterface(projectId,
                request.getAttribute(RequestMappingCommon.REQUEST_ATTRIBUTE_KEY_USER_ID).toString(),json)
    }


    /**
     * 请求接口数据并返回到客户端
     */
    @RequestMapping("/requestInterface")
    fun requestInterfaceAndReturn(@RequestParam("url") url: String): String {
        return HttpConnectCommon.interfaceRequest(url)
    }
}