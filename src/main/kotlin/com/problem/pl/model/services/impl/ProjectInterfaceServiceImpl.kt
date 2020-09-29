package com.problem.pl.model.services.impl

import com.problem.pl.commons.GsonCommon
import com.problem.pl.commons.ResultCommon
import com.problem.pl.commons.UniversalCommon
import com.problem.pl.controller.InterfaceRequestParam
import com.problem.pl.model.dao.ProjectInterfaceMapper
import com.problem.pl.model.dao.ProjectMapper
import com.problem.pl.model.dao.ProjectOperateRecordMapper
import com.problem.pl.model.dao.UserMapper
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectInterfaceEntity
import com.problem.pl.model.entities.TProjectOperateRecorderEntity
import com.problem.pl.model.services.ProjectInterfaceService
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

    @Resource
    lateinit var projectInterfaceMapper: ProjectInterfaceMapper

    @Resource
    lateinit var projectMapper: ProjectMapper

    @Resource
    lateinit var userMapper: UserMapper

    @Resource
    lateinit var projectOperateRecordMapper: ProjectOperateRecordMapper


    /**
     * 保存一个接口
     */
    @Transactional
    override fun saveProjectInterface(projectId: String, uid: String, json: String): ResultPro<TProjectInterfaceEntity> {
        try {
            //解析json
            val requestParams = GsonCommon.gson.fromJson<InterfaceRequestParam>(json,InterfaceRequestParam::class.java) ?:
                    return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "接口参数格式错误！")

            //查询项目
            val projectEntity = projectMapper.queryProjectById(projectId) ?:
                return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "项目不存在，请确认！")

            //查询用户
            val userInfoEntity = userMapper.loadUserByUId(uid) ?:
            return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "用户不存在，请确认！")

            val dbInterfaceId = UniversalCommon.generateDBId()
            //插入接口
            val insertNum = projectInterfaceMapper.saveProjectInterface(TProjectInterfaceEntity().apply {
                this.id = dbInterfaceId
                this.piAddTimestamp = UniversalCommon.generateTimestamp()
                this.piDataJson = json
                this.piName = requestParams.interfaceTitle
                this.projectId = projectEntity.id
                this.userId = userInfoEntity.id
            })

            //插入项目记录
            val insertOperateNum = projectOperateRecordMapper.insertProjectOperateRecords(ArrayList<TProjectOperateRecorderEntity>().apply {
                add(TProjectOperateRecorderEntity().apply {
                    this.id = UniversalCommon.generateDBId()
                    this.projectId = projectEntity.id
                    this.projectName = projectEntity.projectName
                    this.projectInterfaceId = dbInterfaceId
                    this.tporOperateType = TProjectOperateRecorderEntity.OPERATE_TYPE_CREATE
                    this.tporOperateContent = "添加新接口=>${requestParams.interfaceTitle}"
                    this.tporTimestamp = UniversalCommon.generateTimestamp()
                    this.userId = uid
                })
            })

            return if (insertNum > 0 && insertOperateNum > 0) {
                ResultCommon.generateResult()
            } else {
                ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "保存失败，请重试！")
            }
        } catch (e: Exception) {
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
            val queryProjectInterfaceById = projectInterfaceMapper.queryProjectInterfaceById(interfaceId)
            ResultCommon.generateResult(data = queryProjectInterfaceById)
        } catch (e: Exception) {
            ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "${e.message}")
        }
    }
}