package com.problem.pl.model.entities

/**
 * 项目问题实体类
 */
open class TProjectProblemEntity {

    var id: String = ""

    var ppChooseTimestamp: Long = 0

    var ppCompleteTimestamp: Long = 0

    var ppAddTimestamp: Long = 0

    var ppCompleteSchedule: Int = 0

    var ppModulePage: String = ""

    var ppContent: String = ""

    var ppTransferFlow: String = ""

    var projectId: String? = null

    var userIdForChoose: String? = null

    var userIdForAdd: String? = null
    
    var systemDevicesId: String? = null
    
    var refTProjectEntity: TProjectEntity? = null
    
    var chooseProblemTUserEntity: TUserEntity? = null
    
    var addProblemTUserEntity: TUserEntity? = null
    
    var refTProjectSystemDevicesEntity: TProjectSystemDevicesEntity? = null


    override fun toString(): String {
        return "TProjectProblemEntity(id='$id', ppChooseTimestamp=$ppChooseTimestamp, ppCompleteTimestamp=$ppCompleteTimestamp, ppAddTimestamp=$ppAddTimestamp, ppCompleteSchedule=$ppCompleteSchedule, ppModulePage='$ppModulePage', ppContent='$ppContent', ppTransferFlow='$ppTransferFlow', projectId=$projectId, userIdForChoose=$userIdForChoose, userIdForAdd=$userIdForAdd, systemDevicesId=$systemDevicesId, refTProjectEntity=$refTProjectEntity, chooseProblemTUserEntity=$chooseProblemTUserEntity, addProblemTUserEntity=$addProblemTUserEntity, refTProjectSystemDevicesEntity=$refTProjectSystemDevicesEntity)"
    }


}

