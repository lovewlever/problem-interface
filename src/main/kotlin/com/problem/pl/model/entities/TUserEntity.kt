package com.problem.pl.model.entities

/**
 * 用户表Entity
 */
open class TUserEntity {

    var id: String = ""
    var uLoginAccount: String = ""
    var uBindEmail: String = ""
    var uBindPhone: String = ""
    var uLoginPwd: String = ""
    var uAvatar: String = ""
    var uNickname: String = ""
    var uLevel: Int = -1
    var uAge: Int = -1
    var uGender: Any = ""
    var registerTimestamp: Long = -1
    var registerDevices: String = ""
    var registerIp: String = ""
    var lastLoginTimestamp: Long = -1
    var lastLoginDevices: String = ""
    var lastLoginIp: String = ""
    var registerDevicesUserAgentString: String = ""
    var lastLoginDevicesUserAgentString: String = ""
    var token: String = ""


    override fun toString(): String {
        return "TUserEntity(id='$id', uLoginAccount='$uLoginAccount', uBindEmail='$uBindEmail', uBindPhone='$uBindPhone', uLoginPwd='$uLoginPwd', uAvatar='$uAvatar', uNickname='$uNickname', uLevel=$uLevel, uAge=$uAge, uGender=$uGender, registerTimestamp=$registerTimestamp, registerDevices='$registerDevices', registerIp='$registerIp', lastLoginTimestamp=$lastLoginTimestamp, lastLoginDevices='$lastLoginDevices', lastLoginIp='$lastLoginIp', registerDevicesUserAgentString='$registerDevicesUserAgentString', lastLoginDevicesUserAgentString='$lastLoginDevicesUserAgentString', token='$token')"
    }


}

