package com.problem.pl.model.services

import com.problem.pl.controller.RequestSaveProblemEntity
import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectProblemEntity
import com.problem.pl.model.entities.TProjectSystemDevicesEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpServletRequest

@Service
interface ProjectProblemService {

    fun querySystemDevicesList(): ResultPro<TProjectSystemDevicesEntity>

    fun insertProjectProblems(uid: String, userName: String, problems: MutableList<RequestSaveProblemEntity>): ResultPro<Int>

    /**
     * 查询我选中未修改完的问题
     */
    fun queryMineNotCompletedProblems(uid: String,curPage: Int,pageCountSize: Int): ResultPro<TProjectProblemEntity>

    /**
     * 查询我选中已经修改完的问题
     */
    fun queryMineCompletedProblems(uid: String,curPage: Int,pageCountSize: Int): ResultPro<TProjectProblemEntity>


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
    fun chooseOrCancelProblem(uid: String, userName: String, problemId: String,operatingType: String): ResultPro<TProjectProblemEntity>


    /**
     * 修改问题进度
     */
    fun updateModifyProblemProgress(uid: String, userName: String, problemId: String,schedule: Int): ResultPro<TProjectProblemEntity>

    /**
     * 转让问题给某个用户
     */
    fun updateTransferIssues(uid: String,toUserId: String,problemId: String): ResultPro<TProjectProblemEntity>

    /**
     * 编辑修改问题
     */
    fun updateEditModifyProblem(uid: String,
                                userName: String,
                                problemId: String,
                                problemModulePage: String,
                                problemContent: String,
                                systemDevicesId: String): ResultPro<TProjectProblemEntity>


    /**
     * 查询要导出的问题
     */
    fun exportProblemToTxt(type: Int,fileId: String): String
}