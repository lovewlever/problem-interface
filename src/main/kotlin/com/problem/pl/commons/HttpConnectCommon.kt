package com.problem.pl.commons

import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection


object HttpConnectCommon {

    fun interfaceRequest(url: String): String {
        val sb = StringBuilder()
        try {
            val str = url
            //2:声明url这个对象，用于接收一个连接的字符串
            val url = URL(str)
            //3:获取连接
            val con: URLConnection = url.openConnection()
            //4:将con转成HttpUrlConnection
            val hcon: HttpURLConnection = con as HttpURLConnection
            //5:打开连接
            hcon.connect()
            //6:从服务器获取状态码
            val code: Int = hcon.getResponseCode()
            //7:判断是否是200,如果是200则就从服务器上读取信息
            if (code == 200) {
                //8:从服务器获取io,读取从服务器返回的html文本
                val `in`: InputStream = hcon.getInputStream()
                val b = ByteArray(1024)
                var len = 0
                while (`in`.read(b).also({ len = it }) != -1) {
                    val s = String(b, 0, len)
                    sb.append(s)
                }
            }
            //9:关闭连接
            hcon.disconnect()
        } catch (e: Exception) {
            sb.append(e.message)
        }

        return sb.toString()
    }
}