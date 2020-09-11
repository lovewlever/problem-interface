package com.problem.pl.model.entities

/**
 * 用户表Entity
 */
open class TUserEntity {

    var id: String = ""
    var uLoginAccount: String = ""
    var uLoginEmail: String = ""
    var uLoginPhone: String = ""
    var uLoginPwd: String = ""
    var uAvatar: String = ""
    var uNickname: String = ""
    var uLevel: Int = -1
    var uAge: Int = -1
    var uGender: Any = ""
    var registerTimestamp: Long = -1
    var registerDevices: String = ""
    var lastLoginTimestamp: Long = -1
    var lastLoginDevices: String = ""
    var registerDevicesUserAgentString: String = ""
    var token: String = ""


    override fun toString(): String {
        return "TUserEntity(id='$id', uLoginAccount='$uLoginAccount', uLoginEmail='$uLoginEmail', uLoginPhone='$uLoginPhone', uLoginPwd='$uLoginPwd', uAvatar='$uAvatar', uNickname='$uNickname', uLevel=$uLevel, uAge=$uAge, uGender=$uGender, registerTimestamp=$registerTimestamp, registerDevices='$registerDevices', lastLoginTimestamp=$lastLoginTimestamp, lastLoginDevices='$lastLoginDevices', registerDevicesUserAgentString='$registerDevicesUserAgentString', token='$token')"
    }


}

