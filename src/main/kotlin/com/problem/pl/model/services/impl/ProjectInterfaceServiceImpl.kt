package com.problem.pl.model.services.impl

import com.google.gson.JsonObject
import com.google.gson.internal.LinkedTreeMap
import com.problem.pl.commons.GsonCommon
import com.problem.pl.commons.ResultCommon
import com.problem.pl.commons.UniversalCommon
import com.problem.pl.controller.InterfaceRequestParam
import com.problem.pl.model.dao.*
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectInterfaceEntity
import com.problem.pl.model.entities.TProjectOperateRecorderEntity
import com.problem.pl.model.services.ProjectInterfaceService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.interceptor.TransactionAspectSupport
import java.lang.Exception
import javax.annotation.Resource

/**
 * 项目接口Service
 */
@Service
class ProjectInterfaceServiceImpl: ProjectInterfaceService {

    private val log = LoggerFactory.getLogger(ProjectInterfaceServiceImpl::class.java)

    @Resource
    lateinit var projectInterfaceMapper: ProjectInterfaceMapper

    @Resource
    lateinit var projectMapper: ProjectMapper

    @Resource
    lateinit var userMapper: UserMapper

    @Resource
    lateinit var projectOperateRecordMapper: ProjectOperateRecordMapper

    @Resource
    lateinit var projectInterfaceCSMapper: ProjectInterfaceCSMapper


    /**
     * 保存一个接口
     */
    @Transactional
    override fun saveProjectInterface(projectId: String, interfaceId: String, uid: String, json: String): ResultPro<TProjectInterfaceEntity> {
        try {
            log.debug("saveProjectInterface-接口参数：${json}")
            //解析json
            val requestParams = GsonCommon.gson.fromJson<InterfaceRequestParam>(json,InterfaceRequestParam::class.java) ?:
                    return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "接口参数格式错误！")
            requestParams.interfaceResponse = requestParams.interfaceResponse.toString()
            log.debug("saveProjectInterface-解析接口参数：${GsonCommon.gson.toJson(requestParams)}")

            //查询项目
            val projectEntity = projectMapper.queryProjectById(projectId) ?:
                return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "项目不存在，请确认！")

            log.debug("saveProjectInterface-根据项目id查询项目实体：${GsonCommon.gson.toJson(projectEntity)}")

            //查询用户
            val userInfoEntity = userMapper.loadUserByUId(uid) ?:
            return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "用户不存在，请确认！")
            log.debug("saveProjectInterface-根据用户id查询用户实体：${GsonCommon.gson.toJson(userInfoEntity)}")

            log.debug("saveProjectInterface-判断接口是否已经存在：")
            //查询要保存的接口是否已经存在 则更新
            projectInterfaceMapper.queryProjectInterfaceById(interfaceId)?.apply {
                this.piAddTimestamp = UniversalCommon.generateTimestamp()
                this.piDataJson = json
                this.piName = requestParams.interfaceTitle
                val updateInterface = projectInterfaceMapper.updateInterface(this)
                log.debug("saveProjectInterface-接口是否已经存在-更新接口：${updateInterface}")
                return if (updateInterface > 0) {
                    log.debug("saveProjectInterface-更新接口成功")
                    ResultCommon.generateResult()
                } else {
                    log.debug("saveProjectInterface-更新接口成功失败：${updateInterface}")
                    ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "保存失败，请重试！")
                }
            }


            log.debug("saveProjectInterface-插入接口：")
            val dbId = UniversalCommon.generateDBId()
            //插入接口
            val insertNum = projectInterfaceMapper.saveProjectInterface(TProjectInterfaceEntity().apply {
                this.id = dbId
                this.piAddTimestamp = UniversalCommon.generateTimestamp()
                this.piDataJson = json
                this.piName = requestParams.interfaceTitle
                this.projectId = projectEntity.id
                this.userId = userInfoEntity.id
            })
            log.debug("saveProjectInterface-插入接口返回影响数量：${insertNum}")
            log.debug("saveProjectInterface-插入项目操作记录：")

            //插入项目记录
            val insertOperateNum = projectOperateRecordMapper.insertProjectOperateRecords(ArrayList<TProjectOperateRecorderEntity>().apply {
                add(TProjectOperateRecorderEntity().apply {
                    this.id = UniversalCommon.generateDBId()
                    this.projectId = projectEntity.id
                    this.projectName = projectEntity.projectName
                    this.projectInterfaceId = dbId
                    this.tporOperateType = TProjectOperateRecorderEntity.OPERATE_TYPE_CREATE
                    this.tporOperateContent = "添加新接口=>${requestParams.interfaceTitle}"
                    this.tporTimestamp = UniversalCommon.generateTimestamp()
                    this.userId = uid
                })
            })

            log.debug("saveProjectInterface-插入项目操作记录返回影响数量：${insertOperateNum}")

            return if (insertNum > 0 && insertOperateNum > 0) {
                log.debug("saveProjectInterface-保存成功")
                ResultCommon.generateResult()
            } else {
                log.debug("saveProjectInterface-保存失败：${insertNum}---${insertOperateNum}")
                ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "保存失败，请重试！")
            }
        } catch (e: Exception) {
            log.error("saveProjectInterface-Catch:错误回滚:",e)
            //回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
            return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "${e.message}")
        }
    }

    /**
     * 分页查询项目下的接口
     */
    override fun queryProjectInterfacesByProjectId(projectId: String, curPage: Int, pageSize: Int): ResultPro<TProjectInterfaceEntity> {
        return try {
            val projectInterfacesByProjectIdCount = projectInterfaceMapper.getProjectInterfacesByProjectIdCount(projectId)
            val pageE = UniversalCommon.pagingCalculation(curPage, pageSize, projectInterfacesByProjectIdCount)
            val queryProjectInterfacesByProjectId = projectInterfaceMapper.queryProjectInterfacesByProjectId(projectId, pageE.startPos, pageSize)
            ResultCommon.generateResult(data = queryProjectInterfacesByProjectId,pagination = pageE)
        } catch (e: Exception) {
            ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "${e.message}")
        }
    }

    /**
     * 根据id查询接口详情
     */
    override fun queryProjectInterfaceById(interfaceId: String): ResultPro<TProjectInterfaceEntity> {
        return try {
            log.info("queryProjectInterfaceById-参数InterfaceId:${interfaceId}")
            val queryProjectInterfaceById = projectInterfaceMapper.queryProjectInterfaceById(interfaceId)
            //查询该接口的评分
            val theAverageScore = projectInterfaceCSMapper.getTheAverageScoreByInterfaceId(interfaceId)
            var averageStr = "暂无评分"
            if (theAverageScore > 0.0 && theAverageScore <= 20.0) {
                averageStr = "差强人意"
            } else if (theAverageScore > 20.0 && theAverageScore <= 70.0) {
                averageStr = "褒贬不一"
            } else if (theAverageScore > 70.0 && theAverageScore <= 100.0) {
                averageStr = "好评如潮"
            }
            log.info("queryProjectInterfaceById-评分:${theAverageScore}->${averageStr}")
            queryProjectInterfaceById?.theAverageScoreStr = averageStr
            ResultCommon.generateResult(data = queryProjectInterfaceById)
        } catch (e: Exception) {
            ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "${e.message}")
        }
    }
}