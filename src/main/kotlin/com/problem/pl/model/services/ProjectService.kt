package com.problem.pl.model.services

import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectEntity
import org.springframework.stereotype.Service

@Service
interface ProjectService {

    fun queryPListByPagination(page: Int,pageCount: Int): ResultPro<TProjectEntity>

    fun saveNewProjectInfo(projectName: String,uid: String): ResultPro<String>

    fun modifyProjectPriority(priority: Int,projectId: String): ResultPro<String>

    fun modifyProjectName(projectName: String,projectId: String): ResultPro<String>

    fun deleteProjectById(projectId: String): ResultPro<String>
}