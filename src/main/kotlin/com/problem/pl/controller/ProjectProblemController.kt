package com.problem.pl.controller

import com.problem.pl.commons.RequestMappingCommon
import com.problem.pl.commons.ResultCommon
import com.problem.pl.commons.UniversalCommon
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectEntity
import com.problem.pl.model.entities.TProjectProblemEntity
import com.problem.pl.model.entities.TProjectSystemDevicesEntity
import com.problem.pl.model.services.ProjectProblemService
import com.problem.pl.model.services.ProjectService
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.*
import java.net.URLEncoder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


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
     * 查询问题页面的顶部项目标签，
     * 分页查询
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PPC_PROJECT_LABEL_FOR_PROBLEM_BY_PAGINATION)
    fun queryProjectToLabelsByPagination(@RequestParam("page") page: Int,
                                         @RequestParam("pageCountSize") pageCountSize: Int) =
            projectService.queryProjectToLabelsByPagination(page, pageCountSize)

    /**
     * 查询系统设备列表
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PPC_PROJECT_SYSTEM_DEVICES)
    fun querySystemDevicesList(): ResultPro<TProjectSystemDevicesEntity> =
            projectProblemService.querySystemDevicesList()


    /**
     * 根据项目id查询问题列表
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PPC_QUERY_PROBLEM_LIST_BY_PROJECT_ID)
    fun queryProjectProblemsListByProjectId(@RequestParam("projectId") projectId: String,
                                            @RequestParam("page") page: Int,
                                            @RequestParam("pageCountSize") pageCountSize: Int): ResultPro<TProjectProblemEntity> =
            projectProblemService.queryProjectProblemsListByProjectId(projectId, page, pageCountSize)


    /**
     * 查询我已修改完成或选中未修改完成的问题列表
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PPC_QUERY_MINE_COMPLETED_OR_NOT_COMPLETED_PROBLEMS)
    fun queryMineCompletedOrNotCompletedProblems(@RequestParam("findType") findType: String,
                                                 @RequestParam("page") page: Int,
                                                 @RequestParam("pageCountSize") pageCountSize: Int,
                                                 request: HttpServletRequest): ResultPro<TProjectProblemEntity> {
        val uid = request.getAttribute(RequestMappingCommon.REQUEST_ATTRIBUTE_KEY_USER_ID).toString()
        return when (findType) {
            "NotCompleted" -> {
                projectProblemService.queryMineNotCompletedProblems(uid, page, pageCountSize)
            }
            "Completed" -> {
                projectProblemService.queryMineCompletedProblems(uid, page, pageCountSize)
            }
            else -> {
                ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL, msg = "findType参数错误")
            }
        }
    }


    /**
     * 查询最新的指定条问题
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PPC_QUERY_PROBLEM_LIST_NEW_TIME_COUNT)
    fun queryNewProblems(@RequestParam("pageCountSize") pageCountSize: Int): ResultPro<TProjectProblemEntity> =
            projectProblemService.queryNewProblems(pageCountSize)


    /**
     * 保存项目问题
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PPC_PROJECT_SAVE_PROBLEM_LIST)
    fun saveProjectProblems(@RequestBody entities: MutableList<RequestSaveProblemEntity>,
                            request: HttpServletRequest): ResultPro<Int> {

        return projectProblemService.insertProjectProblems(
                request.getAttribute(RequestMappingCommon.REQUEST_ATTRIBUTE_KEY_USER_ID).toString(),
                request.getAttribute(RequestMappingCommon.REQUEST_ATTRIBUTE_KEY_USER_NAME).toString(),
                entities)
        /*val br = BufferedReader(InputStreamReader(request.inputStream))
        val sb = StringBuilder()
        var str: String?
        while (br.readLine().also { str = it } != null) {
            sb.append(str)
        }*/
    }

    /**
     * 选择或取消修改某个问题
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PPC_CHOOSE_OR_CANCEL_PROBLEM)
    fun chooseOneProjectProblem(@RequestParam("operatingType") operatingType: String,
                                @RequestParam("problemId") problemId: String,
                                request: HttpServletRequest): ResultPro<TProjectProblemEntity> {
        return projectProblemService.chooseOrCancelProblem(
                request.getAttribute(RequestMappingCommon.REQUEST_ATTRIBUTE_KEY_USER_ID).toString(),
                request.getAttribute(RequestMappingCommon.REQUEST_ATTRIBUTE_KEY_USER_NAME).toString(),
                problemId, operatingType)
    }

    /**
     * 修改问题进度
     * 100为已完成
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PPC_UPDATE_MODIFY_PROBLEM_PROGRESS)
    fun updateModifyProblemProgress(@RequestParam("problemId") problemId: String,
                                    @RequestParam("schedule") schedule: Int,
                                    request: HttpServletRequest): ResultPro<TProjectProblemEntity> {
        return projectProblemService.updateModifyProblemProgress(
                request.getAttribute(RequestMappingCommon.REQUEST_ATTRIBUTE_KEY_USER_ID).toString(),
                request.getAttribute(RequestMappingCommon.REQUEST_ATTRIBUTE_KEY_USER_NAME).toString(),
                problemId, schedule)
    }


    /**
     * 转让问题给某个用户
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PPC_UPDATE_TRANSFER_ISSUES)
    fun transferIssues(@RequestParam("toUserId") toUserId: String,
                       @RequestParam("problemId") problemId: String,
                       request: HttpServletRequest): ResultPro<TProjectProblemEntity> {
        return projectProblemService.updateTransferIssues(
                request.getAttribute(RequestMappingCommon.REQUEST_ATTRIBUTE_KEY_USER_ID).toString(),
                toUserId,
                problemId)
    }

    /**
     * 编辑修改某个问题
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PPC_UPDATE_EDIT_MODIFY_PROBLEM)
    fun updateEditModifyProblem(@RequestParam("problemId") problemId: String,
                                @RequestParam("problemModulePage") problemModulePage: String,
                                @RequestParam("problemContent") problemContent: String,
                                @RequestParam("systemDevicesId") systemDevicesId: String,
                                request: HttpServletRequest): ResultPro<TProjectProblemEntity> {
        return projectProblemService.updateEditModifyProblem(request.getAttribute(RequestMappingCommon.REQUEST_ATTRIBUTE_KEY_USER_ID).toString(),
                request.getAttribute(RequestMappingCommon.REQUEST_ATTRIBUTE_KEY_USER_NAME).toString(),
                problemId,
                problemModulePage,
                problemContent,
                systemDevicesId)
    }

    /**
     * 导出问题为Txt文件
     * [type]complete: 导出已完成问题，undone：导出未完成问题，all：导出全部问题
     */
    @RequestMapping(RequestMappingCommon.MAPPING_PPC_EXPORT_PROBLEM_DOC)
    fun exportProblemToTxt(@RequestParam("type") type: String,
                           @RequestParam("projectId") projectId: String,
                           @RequestParam("fileId") fileId: String,
                           response: HttpServletResponse) {
        val path = projectProblemService.exportProblemToTxt(type, fileId, projectId)
        val filename = "${UniversalCommon.generateTimestamp()}.txt"
        UniversalCommon.downloadFileToClient(response,filename,path)
    }

}


class RequestSaveProblemEntity(val problemChooseDeviceId: String,
                               val problemModulePage: String,
                               val problemContent: String,
                               val projectId: String)