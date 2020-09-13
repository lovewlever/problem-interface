package com.problem.pl.model.entities

/**
 * 项目表实体类
 */
open class TProjectEntity {
    var id: String = ""
    var projectName: String = ""
    var projectLevel: Int = 0
    var projectAddTimestamp: Long = 0
    var projectCompleteSchedule: Double = 0.toDouble()
    var projectDesc: String = ""
    var projectIsPublic: String = ""
    var userId: String = ""
    var tUserEntity: TUserEntity? = null
}

