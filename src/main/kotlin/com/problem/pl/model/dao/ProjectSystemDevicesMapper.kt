package com.problem.pl.model.dao

import com.problem.pl.model.entities.TProjectSystemDevicesEntity

/**
 * 系统终端Mapper
 */
interface ProjectSystemDevicesMapper {

    fun querySystemDevicesList(): MutableList<TProjectSystemDevicesEntity>

}