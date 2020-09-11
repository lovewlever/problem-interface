package com.problem.pl.model.services.impl

import com.problem.pl.commons.ResultCommon
import com.problem.pl.commons.UniversalCommon
import com.problem.pl.model.dao.ProjectMapper
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectEntity
import com.problem.pl.model.services.ProjectService
import org.springframework.stereotype.Service
import javax.annotation.Resource

@Service("projectService")
class ProjectServiceImpl: ProjectService {

    @Resource
    lateinit var projectMapper: ProjectMapper

    /**
     * 分页查询项目
     */
    override fun queryPListByPagination(page: Int,pageCount: Int): ResultPro<TProjectEntity> {
        return try {
            val findProjectTotalCount = projectMapper.findProjectTotalCount()
            val pageE = UniversalCommon.pagingCalculation(page, pageCount, findProjectTotalCount)
            ResultCommon.generateResult(pagination = pageE,data = projectMapper.queryPListByPagination(pageE.startPos,pageE.endPos))
        } catch (e: Exception) {
            ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "${e.message}")
        }
    }

    /**
     * 添加保存新的项目
     */
    override fun saveNewProjectInfo(projectName: String,uid: String): ResultPro<String> {
        try {
            val saveNewProjectInfo = projectMapper.saveNewProjectInfo(TProjectEntity().apply {
                this.id = UniversalCommon.generateDBId()
                this.projectAddTimestamp = UniversalCommon.generateTimestamp()
                this.projectAddUserId = uid
                this.projectCompleteSchedule = 0.0
                this.projectName = projectName
                this.projectLevel = 0
            })
            return if (saveNewProjectInfo > 0) {
                ResultCommon.generateResult(msg = "项目添加成功")
            } else {
                ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "项目添加失败")
            }
        } catch (e: Exception) {
            return ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "${e.message}")
        }
    }
}