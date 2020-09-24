package com.problem.pl.model.entities


open class TProjectInterfaceEntity {

    var id: String = ""

    var piAddTimestamp: Long = 0

    var piLastModTimestamp: Long = 0

    var piName: String = ""

    var piIsAbandoned: String = "N"

    var piDataJson: String = "{}"

    var piModTransferFlow: String = ""

    var userIdForAdd: String? = null

    var projectId: String? = null

    var refTUserEntity: TUserEntity? = null

    var refTProjectEntity: TProjectEntity? = null

}

