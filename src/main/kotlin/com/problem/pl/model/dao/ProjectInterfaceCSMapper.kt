package com.problem.pl.model.dao

import com.problem.pl.model.entities.TProjectInterfaceCSEntity

/**
 * 项目接口评论/评分Mapper
 */
interface ProjectInterfaceCSMapper {

    /**
     * 插入一条评分信息
     */
    fun insertScoreByInterfaceId(projectInterfaceScoreEntity: TProjectInterfaceCSEntity): Int

    /**
     * 更新一条评论
     */
    fun updateScore(projectInterfaceScoreEntity: TProjectInterfaceCSEntity): Int

    /**
     * 获取接口的平均分
     */
    fun getTheAverageScoreByInterfaceId(interfaceId: String): Double

    /**
     * 查询查询该用户评论对某个接口评论的Entity
     */
    fun queryScoreByInterfaceIdAndUid(interfaceId: String, uid: String): TProjectInterfaceCSEntity?
}