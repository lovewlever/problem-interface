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


    /**
     * 保存一个接口
     */
    @Transactional
    override fun saveProjectInterface(projectId: String, interfaceId: String, uid: String, json: String): ResultPro<TProjectInterfaceEntity> {
        try {
            log.info("saveProjectInterface-接口参数：${json}")
            //解析json
            val requestParams = GsonCommon.gson.fromJson<InterfaceRequestParam>(json,InterfaceRequestParam::class.java) ?:
                    return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "接口参数格式错误！")
            log.info("saveProjectInterface-解析接口参数：${GsonCommon.gson.toJson(requestParams)}")

            //查询项目
            val projectEntity = projectMapper.queryProjectById(projectId) ?:
                return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "项目不存在，请确认！")

            log.info("saveProjectInterface-根据项目id查询项目实体：${GsonCommon.gson.toJson(projectEntity)}")

            //查询用户
            val userInfoEntity = userMapper.loadUserByUId(uid) ?:
            return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "用户不存在，请确认！")
            log.info("saveProjectInterface-根据用户id查询用户实体：${GsonCommon.gson.toJson(userInfoEntity)}")

            var dbInterfaceId = UniversalCommon.generateDBId()
            //查询要保存的接口是否已经存在 则删除
            projectInterfaceMapper.queryProjectInterfaceById(interfaceId)?.let {
                dbInterfaceId = it.id
                val deleteInterfaceNum = projectInterfaceMapper.deleteInterfaceById(interfaceId)
                log.info("saveProjectInterface-删除已存在的接口：${deleteInterfaceNum}")
            }


            log.info("saveProjectInterface-生成数据库ID：${dbInterfaceId}")
            log.info("saveProjectInterface-插入接口：")
            //插入接口
            val insertNum = projectInterfaceMapper.saveProjectInterface(TProjectInterfaceEntity().apply {
                this.id = dbInterfaceId
                this.piAddTimestamp = UniversalCommon.generateTimestamp()
                this.piDataJson = json
                this.piName = requestParams.interfaceTitle
                this.projectId = projectEntity.id
                this.userId = userInfoEntity.id
            })
            log.info("saveProjectInterface-插入接口返回影响数量：${insertNum}")
            log.info("saveProjectInterface-插入项目操作记录：")

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

            log.info("saveProjectInterface-插入项目操作记录返回影响数量：${insertOperateNum}")

            return if (insertNum > 0 && insertOperateNum > 0) {
                log.info("saveProjectInterface-保存成功")
                ResultCommon.generateResult()
            } else {
                log.info("saveProjectInterface-保存失败：${insertNum}---${insertOperateNum}")
                ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "保存失败，请重试！")
            }
        } catch (e: Exception) {
            log.info("saveProjectInterface-Catch:错误回滚:${e.stackTrace}")
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
            ResultCommon.generateResult(data = queryProjectInterfaceById)
        } catch (e: Exception) {
            ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "${e.message}")
        }
    }
}