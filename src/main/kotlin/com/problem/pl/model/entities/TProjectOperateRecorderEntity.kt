package com.problem.pl.model.entities


/**
 * 对项目操作的记录
 */
open class TProjectOperateRecorderEntity {
    var id: String = ""
    var tporTimestamp: Long = 0L
    var tporOperateType: String = ""
    var tporProjectName: String = ""
    var tporOperateContent: String = ""
    var tporProjectId: String = ""
    var tporModUserId: String = ""
    var refTUserEntity: TUserEntity? = null


    override fun toString(): String {
        return "TProjectOperateRecorderEntity(id='$id', tporTimestamp=$tporTimestamp, tporOperateType='$tporOperateType', tporProjectName='$tporProjectName', tporOperateContent='$tporOperateContent', tporProjectId='$tporProjectId', tporModUserId='$tporModUserId', refTUserEntity=$refTUserEntity)"
    }


}

