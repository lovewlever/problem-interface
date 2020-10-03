package com.problem.pl.model.services.impl

import com.problem.pl.commons.ResultCommon
import com.problem.pl.commons.UniversalCommon
import com.problem.pl.model.dao.ProjectInterfaceCSMapper
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectInterfaceCSEntity
import com.problem.pl.model.services.ProjectInterfaceCSService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.interceptor.TransactionAspectSupport
import javax.annotation.Resource

/**
 * 项目接口评论/评分Service
 */
@Service
class ProjectInterfaceCsServiceImpl : ProjectInterfaceCSService {

    private val log = LoggerFactory.getLogger(ProjectInterfaceCsServiceImpl::class.java)

    @Resource
    lateinit var projectInterfaceCSMapper: ProjectInterfaceCSMapper

    /**
     * 添加一条评论/评分
     */
    @Transactional
    override fun addCsToInterface(interfaceId: String, uid: String, points: Int, commentContent: String, isAnonymous: String): ResultPro<TProjectInterfaceCSEntity> {
        log.debug("addCsToInterface-参数：points：${points}--commentContent：${commentContent}--isAnonymous：${isAnonymous}")

        try {
            //如果已经评论过 直接更新评论
            projectInterfaceCSMapper.queryScoreByInterfaceIdAndUid(interfaceId, uid)?.let { entity: TProjectInterfaceCSEntity ->
                log.debug("addCsToInterface-已经评论过,直接更新评论;ID:${entity.id}")
                entity.tisPoints = points
                entity.tisCommentContent = commentContent
                entity.tisIsAnonymous = isAnonymous
                entity.tisAssessTime = UniversalCommon.generateTimestamp()
                val updateScore = projectInterfaceCSMapper.updateScore(entity)

                log.debug("addCsToInterface-已经评论过,直接更新评论结果;Int:${updateScore}")
                return if (updateScore > 0) {
                    ResultCommon.generateResult(msg = "评论/评分成功！")
                } else {
                    ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL, msg = "评论/评分失败！请重试")
                }
            }

            log.debug("addCsToInterface-之前未评论过，插入该评论:${commentContent}")

            val insertScoreByInterfaceId = projectInterfaceCSMapper.insertScoreByInterfaceId(TProjectInterfaceCSEntity().apply {
                this.id = UniversalCommon.generateDBId()
                this.tisPoints = points
                this.tisAssessTime = UniversalCommon.generateTimestamp()
                this.tisCommentContent = commentContent
                this.tisIsAnonymous = isAnonymous
                this.interfaceId = interfaceId
                this.userId = uid
            })
            log.debug("addCsToInterface-插入评论/评分返回：${insertScoreByInterfaceId}")
            return if (insertScoreByInterfaceId > 0) {
                ResultCommon.generateResult(msg = "评论/评分成功！")
            } else {
                ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL, msg = "评论/评分失败！请重试")
            }
        } catch (e: Exception) {
            log.error("addCsToInterface-插入评论/评分返回：", e)
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly() //回滚
            return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL, msg = "${e.message}")
        }
    }


}