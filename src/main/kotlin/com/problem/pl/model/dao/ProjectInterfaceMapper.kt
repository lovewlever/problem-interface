package com.problem.pl.model.dao

import com.problem.pl.model.entities.TProjectInterfaceEntity

/**
 * 项目接口表Mapper
 */
interface ProjectInterfaceMapper {


    /**
     * 保存一个接口
     */
    fun saveProjectInterface(projectInterfaceEntity: TProjectInterfaceEntity): Int

    /**
     * 查询某个项目下的接口列表
     */
    fun queryProjectInterfacesByProjectId(projectId: String,startPos: Int,pageSizeCount: Int): MutableList<TProjectInterfaceEntity>

    /**
     * 查询某个项目下所有接口的数量
     */
    fun getProjectInterfacesByProjectIdCount(projectId: String): Int

    /**
     * 根据接口id查询详情
     */
    fun queryProjectInterfaceById(interfaceId: String): TProjectInterfaceEntity?
}