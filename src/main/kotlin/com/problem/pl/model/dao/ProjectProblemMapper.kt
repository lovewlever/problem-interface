package com.problem.pl.model.dao

import com.problem.pl.model.entities.TProjectEntity
import com.problem.pl.model.entities.TProjectProblemEntity

/**
 * 项目管理Mapper
 */
interface ProjectProblemMapper {

    fun insertBatchProjectProblems(tppes: MutableList<TProjectProblemEntity>): Int
}