package com.problem.pl.model.dao

import com.problem.pl.model.entities.TProjectInterfaceEntity

/**
 * 项目接口表Mapper
 */
interface ProjectInterfaceMapper {

    /**
     * 根据id相差一个接口
     */
    fun queryInterfaceById(interfaceId: String): TProjectInterfaceEntity?

    /**
     * 保存一个接口
     */
    fun saveProjectInterface(projectInterfaceEntity: TProjectInterfaceEntity): Int
}