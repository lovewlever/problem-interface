package com.problem.pl.model.dao

import com.problem.pl.model.entities.TProjectOperateRecorderEntity

/**
 * 项目操作记录 Mapper
 */
interface ProjectOperateRecordMapper {

    fun insertProjectOperateRecord(tProjectOperateRecorderEntity: TProjectOperateRecorderEntity): Int
}