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

    var projectId: String = ""

    var userIdForChoose: String = ""

    var userIdForAdd: String = ""
    
    var systemDevicesId: String = ""
    
    var refTProjectEntity: TProjectEntity? = null
    
    var chooseProblemTUserEntity: TUserEntity? = null
    
    var addProblemTUserEntity: TUserEntity? = null
    
    var refTProjectSystemDevicesEntity: TProjectSystemDevicesEntity? = null
    

}

