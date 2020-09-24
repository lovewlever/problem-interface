package com.problem.pl.commons

import com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import java.util.*
import javax.net.ssl.HttpsURLConnection


object HttpConnectCommon {

    fun interfaceRequest(url: String) {
        val str = "http://www.baidu.com"
        //2:声明url这个对象，用于接收一个连接的字符串
        //2:声明url这个对象，用于接收一个连接的字符串
        val url = URL(str)
        //3:获取连接
        //3:获取连接
        val con: URLConnection = url.openConnection()
        //4:将con转成HttpUrlConnection
        //4:将con转成HttpUrlConnection
        val hcon: HttpURLConnection = con as HttpURLConnection
        //5:打开连接
        //5:打开连接
        hcon.connect()
        //6:从服务器获取状态码
        //6:从服务器获取状态码
        val code: Int = hcon.getResponseCode()
        System.err.println("code is:$code")
        //7:判断是否是200,如果是200则就从服务器上读取信息
        //7:判断是否是200,如果是200则就从服务器上读取信息
        if (code == 200) {
            //8:从服务器获取io,读取从服务器返回的html文本
            val `in`: InputStream = hcon.getInputStream()
            val b = ByteArray(1024)
            var len = 0
            while (`in`.read(b).also({ len = it }) != -1) {
                val s = String(b, 0, len)
                System.err.println(s)
            }
        }
        //9:关闭连接
        //9:关闭连接
        hcon.disconnect()
    }

    fun interfaceRequest2(dataUrl: String) {
        var out: OutputStreamWriter? = null
        var `in`: BufferedReader? = null
        var isr: InputStreamReader? = null
        var fileWriter: FileWriter? = null
        var conn: HttpsURLConnection? = null
        try {
            val realUrl = URL(dataUrl)
            conn = realUrl.openConnection() as HttpsURLConnection
            //conn.setSSLSocketFactory(TCITLSSocketConnectionFactory())
            conn.setRequestMethod("POST")
            conn.setDoOutput(true)
            conn.setDoInput(true)
            conn.setUseCaches(false)
            conn.setConnectTimeout(180000)
            conn.setReadTimeout(180000)
            conn.setRequestProperty("accept", "*/*")
            conn.setRequestProperty("connection", "Keep-Alive")
            conn.setRequestProperty("Host", "apps.tampagov.net")
            conn.setRequestProperty("Referer", "https://apps.tampagov.net/CallsForService_Webapp/Default.aspx?type=TPD")
            conn.setRequestProperty("Origin", "https://apps.tampagov.net")
            conn.setRequestProperty("Cookie", "ASP.NET_SessionId=4pz44jsikfvfeu5jgzjeynsz; _ga=GA1.2.969372624.1587627191; __utma=51000313.969372624.1587627191.1587693225.1587693225.1; __utmc=51000313; __utmz=51000313.1587693225.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); ASPSESSIONIDSUTRTQSB=ONMDEOBBKJEFIMBLLJJEPNFL; ASPSESSIONIDQUSQQSSD=FJFFFPBBKPLGBGHPLMMBKHAE; _gid=GA1.2.354408890.1587970939; _gat_gtag_UA_52588819_1=1")
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36")
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")

            conn.setRequestProperty("Sec-Fetch-Dest", "document")
            conn.setRequestProperty("Sec-Fetch-Mode", "navigate")
            conn.setRequestProperty("Sec-Fetch-Site", "Sec-Fetch-Site")
            conn.setRequestProperty("Sec-Fetch-User", "?1")
            conn.setRequestProperty("Upgrade-Insecure-Requests", "1")
            conn.setRequestProperty("Pragma", "no-cache")
            conn.setRequestProperty("Cache-Control", "no-cache")
            conn.setRequestProperty("Content-Length", "40550")
            val map: MutableMap<String, String> = HashMap()
            map!!["__VIEWSTATE"] = "lHP0yp6Wmg73330ijCYTUVeS/hiZKn0lCm60G3t8qfS8/przGSRoYFpSkwDGXZCmpDkmF6isrIm7LBIsaN9t"
            map!!["ctl00\$MainContent\$btndata"] = "Export to Excel"
            map!!["__EVENTVALIDATION"] = "2ZxxriHSoAEU04Tl/4Ta8B+44gZKQ0w/Reo1oqRuER4MxnDqZDjJ+tBnPvABHHkO1XdWBEHhjoqQftKSL3ezi"
            val buffer = StringBuffer()
            for (key in map!!.keys) {
                buffer.append("------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n")
                buffer.append("Content-Disposition: form-data; name=\"")
                buffer.append(key)
                buffer.append("\"\r\n\r\n")
                buffer.append(map!![key])
                buffer.append("\r\n")
            }
            if (map != null) buffer.append("------WebKitFormBoundary7MA4YWxkTrZu0gW--\r\n")

            out = OutputStreamWriter(conn.getOutputStream())
            out.write(buffer.toString())
            out.flush()
            if (conn.getResponseCode() === 200) {
                isr = InputStreamReader(conn.getInputStream())
                `in` = BufferedReader(isr)
                var line: String?
                //fileWriter = FileWriter(xmlFile)
                while (`in`.readLine().also { line = it } != null) {
                    fileWriter!!.write(line)
                }
                fileWriter!!.flush()
                fileWriter?.close()
                isr?.close()
                `in`?.close()
            } else {
                LOGGER.info("Connection failed")
                return
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
        }
    }
}

fun main() {
    HttpConnectCommon.interfaceRequest("")
}