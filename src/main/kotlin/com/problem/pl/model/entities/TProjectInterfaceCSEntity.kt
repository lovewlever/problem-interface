package com.problem.pl.model.entities

/**
 * 项目接口评论/评分表
 */
open class TProjectInterfaceCSEntity {

    lateinit var id: String

    var tisPoints: Int = 10

    var tisAssessTime: Long = 0L

    lateinit var interfaceId: String

    var tisCommentContent: String? = null

    var tisIsAnonymous:String = ""

    lateinit var userId: String

    var refTUserEntity: TUserEntity? = null


    override fun toString(): String {
        return "TProjectInterfaceCSEntity(id='$id', tisPoints=$tisPoints, tisAssessTime=$tisAssessTime, interfaceId='$interfaceId', tisCommentContent=$tisCommentContent, tisIsAnonymous='$tisIsAnonymous', userId='$userId', refTUserEntity=$refTUserEntity)"
    }


}

