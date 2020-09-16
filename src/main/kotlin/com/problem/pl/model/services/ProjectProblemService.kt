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

    /**
     * 查询最新的指定条问题
     */
    fun queryNewProblems(pageCount: Int): ResultPro<TProjectProblemEntity>

    /**
     * 选择问题修改
     */
    fun chooseProblem(uid: String,problemId: String): ResultPro<TProjectProblemEntity>

}