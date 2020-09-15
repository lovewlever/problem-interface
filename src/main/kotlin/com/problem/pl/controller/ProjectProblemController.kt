package com.problem.pl.controller

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor
import com.google.gson.JsonArray
import com.problem.pl.commons.RequestMappingCommon
import com.problem.pl.commons.ResultCommon
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectEntity
import com.problem.pl.model.entities.TProjectSystemDevicesEntity
import com.problem.pl.model.services.ProjectProblemService
import com.problem.pl.model.services.ProjectService
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
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


    /**
     * 保存项目问题
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PPC_PROJECT_SAVE_PROBLEM_LIST)
    fun saveProjectProblems(@RequestBody entities: MutableList<RequestSaveProblemEntity>,
                            request: HttpServletRequest): ResultPro<Int> {

        return projectProblemService.insertProjectProblems(
                request.getAttribute(RequestMappingCommon.REQUEST_ATTRIBUTE_KEY_USER_ID).toString(),entities)
        /*val br = BufferedReader(InputStreamReader(request.inputStream))
        val sb = StringBuilder()
        var str: String?
        while (br.readLine().also { str = it } != null) {
            sb.append(str)
        }*/
    }

}


class RequestSaveProblemEntity(val problemChooseDeviceId: String,
                               val problemModulePage: String,
                               val problemContent: String,
                               val projectId: String)