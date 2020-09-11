package com.problem.pl.model.dao

import com.problem.pl.model.entities.TProjectEntity

interface ProjectMapper {

    fun queryPListByPagination(startPos: Int,entPos: Int): MutableList<TProjectEntity>

    fun saveNewProjectInfo(tProjectEntity: TProjectEntity): Int

    fun findProjectTotalCount(): Int
}