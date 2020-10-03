package com.problem.pl.model.services

import com.problem.pl.model.entities.ResultPro
import com.problem.pl.model.entities.TProjectInterfaceCSEntity
import org.springframework.stereotype.Service

/**
 * 项目接口评论/评分Service
 */
@Service
interface ProjectInterfaceCSService {

    /**
     * 添加一条评论/评分
     */
    fun addCsToInterface(interfaceId: String, uid: String, points: Int, commentContent: String, isAnonymous: String): ResultPro<TProjectInterfaceCSEntity>
}