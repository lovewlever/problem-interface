package com.problem.pl.model.entities


/**
 * 对项目操作的记录
 */
open class TProjectOperateRecorderEntity {
    var id: String = ""
    var tporTimestamp: Long = 0L
    var tporOperateType: String = ""
    var tporName: String = ""
    var tporOperateContent: String = ""
    var userId: String = ""
    var projectId: String = ""
    var projectProblemId: String = ""
    var projectInterfaceId: String = ""
    var refTUserEntity: TUserEntity? = null


    override fun toString(): String {
        return "TProjectOperateRecorderEntity(id='$id', tporTimestamp=$tporTimestamp, tporOperateType='$tporOperateType', tporName='$tporName', tporOperateContent='$tporOperateContent', userId='$userId', projectId='$projectId', projectProblemId='$projectProblemId', projectInterfaceId='$projectInterfaceId', refTUserEntity=$refTUserEntity)"
    }


}

