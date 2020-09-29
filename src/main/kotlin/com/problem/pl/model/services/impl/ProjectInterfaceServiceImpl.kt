package com.problem.pl.model.services.impl

import com.problem.pl.commons.ResultCommon
import com.problem.pl.commons.UniversalCommon
import com.problem.pl.model.dao.ProjectInterfaceMapper
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectInterfaceEntity
import com.problem.pl.model.services.ProjectInterfaceService
import org.springframework.stereotype.Service
import java.lang.Exception
import javax.annotation.Resource

/**
 * 项目接口Service
 */
@Service
class ProjectInterfaceServiceImpl: ProjectInterfaceService {

    @Resource
    lateinit var projectInterfaceMapper: ProjectInterfaceMapper


    /**
     * 保存一个接口
     */
    override fun saveProjectInterface(projectId: String, uid: String, json: String): ResultPro<TProjectInterfaceEntity> {
        projectInterfaceMapper.saveProjectInterface(TProjectInterfaceEntity())
        return ResultCommon.generateResult()
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