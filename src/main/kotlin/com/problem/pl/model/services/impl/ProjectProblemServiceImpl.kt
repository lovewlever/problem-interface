package com.problem.pl.model.services.impl

import com.problem.pl.commons.ResultCommon
import com.problem.pl.model.dao.ProjectSystemDevicesMapper
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectSystemDevicesEntity
import com.problem.pl.model.services.ProjectProblemService
import org.springframework.stereotype.Service
import java.lang.Exception
import javax.annotation.Resource

/**
 * 项目问题
 */
@Service("projectProblemService")
class ProjectProblemServiceImpl: ProjectProblemService {

    @Resource
    lateinit var projectSystemDevicesMapper: ProjectSystemDevicesMapper

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

}