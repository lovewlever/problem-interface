package com.problem.pl.model.services.impl

import com.problem.pl.commons.ResultCommon
import com.problem.pl.commons.UniversalCommon
import com.problem.pl.controller.RequestSaveProblemEntity
import com.problem.pl.model.dao.ProjectMapper
import com.problem.pl.model.dao.ProjectOperateRecordMapper
import com.problem.pl.model.dao.ProjectProblemMapper
import com.problem.pl.model.dao.ProjectSystemDevicesMapper
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectOperateRecorderEntity
import com.problem.pl.model.entities.TProjectProblemEntity
import com.problem.pl.model.entities.TProjectSystemDevicesEntity
import com.problem.pl.model.services.ProjectProblemService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.interceptor.TransactionAspectSupport
import java.lang.Exception
import javax.annotation.Resource

/**
 * 项目问题
 */
@Service("projectProblemService")
class ProjectProblemServiceImpl: ProjectProblemService {

    @Resource
    lateinit var projectMapper: ProjectMapper

    @Resource
    lateinit var projectProblemMapper: ProjectProblemMapper

    @Resource
    lateinit var projectSystemDevicesMapper: ProjectSystemDevicesMapper

    @Resource
    lateinit var projectOperateRecordMapper: ProjectOperateRecordMapper

    /**
     * 查询系统设备列表
     */
    override fun querySystemDevicesList(): ResultPro<TProjectSystemDevicesEntity> {
        return try {
            val querySystemDevicesList = projectSystemDevicesMapper.querySystemDevicesList()
            ResultCommon.generateResult(data = querySystemDevicesList)
        }catch (e: Exception) {
            ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "${e.message}")
        }
    }

    /**
     * 保存本地提交的问题
     * 2.添加项目操作记录
     * 3.存储发送ws消息记录（用户对项目提交了新问题）
     */
    @Transactional
    override fun insertProjectProblems(uid: String, problems: MutableList<RequestSaveProblemEntity>): ResultPro<Int> {
        try {
            if (problems.isNotEmpty()) {
                //查询项目
                projectMapper.queryProjectById(problems[0].projectId)?.let { projectEntity ->
                    //问题集
                    val projectProblems = ArrayList<TProjectProblemEntity>(problems.size)
                    //操作记录集
                    val operates = ArrayList<TProjectOperateRecorderEntity>(problems.size)
                    //循环生成问题表数据实体类
                    problems.forEach { req: RequestSaveProblemEntity ->
                        //问题
                        projectProblems.add(TProjectProblemEntity().apply {
                            this.id = UniversalCommon.generateDBId()
                            this.ppAddTimestamp = UniversalCommon.generateTimestamp()
                            this.ppCompleteSchedule = 0
                            this.ppContent = req.problemContent
                            this.ppModulePage = req.problemModulePage
                            this.projectId = projectEntity.id
                            this.systemDevicesId = req.problemChooseDeviceId
                            this.userIdForAdd = uid
                        })
                        //操作记录
                        operates.add(TProjectOperateRecorderEntity().apply {
                            this.id = UniversalCommon.generateDBId()
                            this.projectId = projectEntity.id
                            this.projectName = projectEntity.projectName
                            this.tporOperateType = TProjectOperateRecorderEntity.OPERATE_TYPE_CREATE
                            this.tporOperateContent = "添加新问题=>${req.problemContent}"
                            this.tporTimestamp = UniversalCommon.generateTimestamp()
                            this.userId = uid
                        })
                    }
                    var insertProblemNum = 0
                    if (projectProblems.isNotEmpty()) {
                        //存储问题
                        insertProblemNum = projectProblemMapper.insertBatchProjectProblems(projectProblems)
                    }
                    if (operates.isNotEmpty()) {
                        //存储操作记录
                        val insertOperateNum = projectOperateRecordMapper.insertProjectOperateRecords(operates)
                    }
                    //存储WS消息
                    // TODO 2020-09-15 暂放


                    return ResultCommon.generateResult(msg = "问题保存成功：${insertProblemNum} 条")
                } ?: let {
                    return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "未查询到问题所对应的项目")
                }
            } else {
                return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "问题列表不能为空为空")
            }
        } catch (e: Exception) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly() //回滚
            return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "${e.message}")
        }
    }

    /**
     * 根据项目id查询 问题列表
     */
    override fun queryProjectProblemsListByProjectId(projectId: String,curPage: Int,pageCountSize: Int): ResultPro<TProjectProblemEntity> {
        return try {
            val findOperateRecordCount = projectProblemMapper.getPPListByProjectIdCount(projectId)
            val pageE = UniversalCommon.pagingCalculation(curPage, pageCountSize, findOperateRecordCount)
            val queryProjectProblemsListByProjectId = projectProblemMapper.queryProjectProblemsListByProjectId(projectId, pageE.startPos, pageE.endPos)
            ResultCommon.generateResult(data = queryProjectProblemsListByProjectId,pagination = pageE)
        } catch (e: Exception) {
            ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "${e.message}")
        }
    }

    /**
     * 查询最新的指定条问题
     */
    override fun queryNewProblems(pageCount: Int): ResultPro<TProjectProblemEntity> {
        return try {
            val queryNewProblems = projectProblemMapper.queryNewProblems(pageCount)
            ResultCommon.generateResult(data = queryNewProblems)
        } catch (e: Exception) {
            ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "${e.message}")
        }
    }

}