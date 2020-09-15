package com.problem.pl.model.entities


/**
 * 对项目操作的记录
 */
open class TProjectOperateRecorderEntity {
    companion object {
        const val OPERATE_TYPE_CREATE = "CREATE"
        const val OPERATE_TYPE_DELETE = "DELETE"
        const val OPERATE_TYPE_MODIFY = "MODIFY"
    }

    var id: String = ""
    var tporTimestamp: Long = 0L
    var tporOperateType: String = ""
    var projectName: String = ""
    var tporOperateContent: String = ""
    var userId: String = ""
    var projectId: String? = null
    var projectProblemId: String? = null
    var projectInterfaceId: String? = null
    var refTUserEntity: TUserEntity? = null


    override fun toString(): String {
        return "TProjectOperateRecorderEntity(id='$id', tporTimestamp=$tporTimestamp, tporOperateType='$tporOperateType', tporName='$projectName', tporOperateContent='$tporOperateContent', userId='$userId', projectId='$projectId', projectProblemId='$projectProblemId', projectInterfaceId='$projectInterfaceId', refTUserEntity=$refTUserEntity)"
    }


}

