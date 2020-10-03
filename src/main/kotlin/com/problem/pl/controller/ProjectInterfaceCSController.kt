package com.problem.pl.controller

import com.problem.pl.commons.RequestMappingCommon
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectInterfaceCSEntity
import com.problem.pl.model.services.ProjectInterfaceCSService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest

/**
 * 项目接口评论/评分Controller
 */
@RequestMapping(RequestMappingCommon.MAPPING_PICS)
@RestController
class ProjectInterfaceCSController {

    private val log = LoggerFactory.getLogger(ProjectInterfaceCSController::class.java)

    @Resource
    lateinit var projectInterfaceCSService: ProjectInterfaceCSService

    /**
     * 添加或修改评分
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PICS_ADD_OR_UPDATE_CS)
    fun addOrUpdateInterfaceCS(@RequestParam("interfaceId") interfaceId: String,
                               @RequestParam("point") points: Int,
                               @RequestParam("commentContent") commentContent: String,
                               @RequestParam("isAnonymous") isAnonymous: String,
                               request: HttpServletRequest):ResultPro<TProjectInterfaceCSEntity> {
        log.debug("addOrUpdateInterfaceCS-参数：")
        return projectInterfaceCSService.addCsToInterface(interfaceId,
                request.getAttribute(RequestMappingCommon.REQUEST_ATTRIBUTE_KEY_USER_ID).toString(),
                points,
                commentContent,
                isAnonymous)
    }
}