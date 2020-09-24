package com.problem.pl.model.services

import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectInterfaceEntity
import org.springframework.stereotype.Service

/**
 * 接口Service
 */
@Service
interface ProjectInterfaceService {

    /**
     * 保存接口
     */
    fun saveProjectInterface(projectId: String,uid: String,json: String): ResultPro<TProjectInterfaceEntity>


}