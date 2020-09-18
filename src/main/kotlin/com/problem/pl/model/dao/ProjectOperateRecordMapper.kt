package com.problem.pl.model.dao

import com.problem.pl.model.entities.TProjectOperateRecorderEntity

/**
 * 项目操作记录 Mapper
 */
interface ProjectOperateRecordMapper {

    fun insertProjectOperateRecord(tProjectOperateRecorderEntity: TProjectOperateRecorderEntity): Int

    /**
     * 根据项目id查询此项目记录
     */
    fun queryProjectOperateRecordsByProjectId(projectId: String,startPos: Int,pageSize: Int): MutableList<TProjectOperateRecorderEntity>

    /**
     * 根据项目id查询 操作的总记录
     */
    fun queryCountSizeByProjectId(projectId: String): Int

    fun insertProjectOperateRecords(operates: MutableList<TProjectOperateRecorderEntity>): Int
}