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
    fun saveProjectInterface(projectId: String,interfaceId: String,uid: String,json: String): ResultPro<TProjectInterfaceEntity>

    /**
     * 分页查询项目下的接口
     */
    fun queryProjectInterfacesByProjectId(projectId: String,curPage: Int,pageSize: Int): ResultPro<TProjectInterfaceEntity>

    /**
     * 根据id查询接口详情
     */
    fun queryProjectInterfaceById(interfaceId: String): ResultPro<TProjectInterfaceEntity>


    fun exportInterfaceToWord(interfaceId: String)
}