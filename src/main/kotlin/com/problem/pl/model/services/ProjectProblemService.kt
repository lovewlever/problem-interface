package com.problem.pl.model.services

import com.problem.pl.controller.RequestSaveProblemEntity
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectProblemEntity
import com.problem.pl.model.entities.TProjectSystemDevicesEntity
import org.springframework.stereotype.Service

@Service
interface ProjectProblemService {

    fun querySystemDevicesList(): ResultPro<TProjectSystemDevicesEntity>

    fun insertProjectProblems(uid: String,problems: MutableList<RequestSaveProblemEntity>): ResultPro<Int>

    /**
     * 根据项目id查询 问题列表
     */
    fun queryProjectProblemsListByProjectId(projectId: String,curPage: Int,pageCountSize: Int): ResultPro<TProjectProblemEntity>


}