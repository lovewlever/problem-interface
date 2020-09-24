package com.problem.pl.model.services.impl

import com.problem.pl.commons.ResultCommon
import com.problem.pl.model.dao.ProjectInterfaceMapper
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectInterfaceEntity
import com.problem.pl.model.services.ProjectInterfaceService
import org.springframework.stereotype.Service
import javax.annotation.Resource

/**
 * 项目接口Service
 */
@Service
class ProjectInterfaceServiceImpl: ProjectInterfaceService {

    @Resource
    lateinit var projectInterfaceMapper: ProjectInterfaceMapper


    override fun saveProjectInterface(projectId: String, uid: String, json: String): ResultPro<TProjectInterfaceEntity> {
        projectInterfaceMapper.saveProjectInterface(TProjectInterfaceEntity())
        return ResultCommon.generateResult()
    }
}