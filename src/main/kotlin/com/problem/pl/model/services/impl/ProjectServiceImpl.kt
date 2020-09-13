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
    override fun saveNewProjectInfo(projectName: String,projectLevel: Int,projectDesc: String,uid: String): ResultPro<String> {
        try {
            val saveNewProjectInfo = projectMapper.saveNewProjectInfo(TProjectEntity().apply {
                this.id = UniversalCommon.generateDBId()
                this.projectAddTimestamp = UniversalCommon.generateTimestamp()
                this.projectAddUserId = uid
                this.projectCompleteSchedule = 0.0
                this.projectName = projectName
                this.projectLevel = projectLevel
                this.projectDesc = projectDesc
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

    /**
     * 修改项目的优先级
     */
    override fun modifyProjectPriority(priority: Int, projectId: String): ResultPro<String> {
        return try {
            val modifyProjectPriority = projectMapper.modifyProjectPriority(priority, projectId)
            if (modifyProjectPriority > 0) {
                ResultCommon.generateResult(msg = "项目优先级调整成功")
            } else {
                ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "项目优先级调整失败")
            }
        } catch (e: Exception) {
            ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "${e.message}")
        }
    }

    /**
     * 修改项目名称
     */
    override fun modifyProjectName(projectName: String, projectId: String): ResultPro<String> {
        return try {
            val modifyProjectPriority = projectMapper.modifyProjectName(projectName, projectId)
            if (modifyProjectPriority > 0) {
                ResultCommon.generateResult(msg = "项目名称修改成功")
            } else {
                ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "项目名称修改失败")
            }
        } catch (e: Exception) {
            ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "${e.message}")
        }
    }

    /**
     * 删除一个项目
     */
    override fun deleteProjectById(projectId: String): ResultPro<String> {
        return try {
            val modifyProjectPriority = projectMapper.deleteProjectById(projectId)
            if (modifyProjectPriority > 0) {
                ResultCommon.generateResult(msg = "项目删除成功")
            } else {
                ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "项目删除失败")
            }
        } catch (e: Exception) {
            ResultCommon.generateResult(code = ResultCommon.RESULT_CODE_FAIL,msg = "${e.message}")
        }
    }
}