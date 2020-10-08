package com.problem.pl.commons

import com.problem.pl.model.entities.PageE
import org.springframework.util.ResourceUtils
import java.io.*
import java.net.InetAddress
import java.net.URLEncoder
import java.net.UnknownHostException
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


object UniversalCommon {

    private const val UNKNOWN = "unknown"
    private const val LOCALHOST = "127.0.0.1"
    private const val SEPARATOR = ","

    fun generateDBId(): String = UUID.randomUUID().toString().replace("-", "")

    fun generateTimestamp() : Long = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli()

    /**
     * 计算分页
     * [curPage] 要计算的当前页
     * [dataTotalCount] 要计算的总数据
     * [pageCountSize] 要查询的每页的数量
     */
    fun pagingCalculation(curPage: Int, pageCountSize: Int, dataTotalCount: Int): PageE {
        //总页数
        val pageSize = Math.ceil(dataTotalCount.toDouble() / pageCountSize.toDouble()).toInt()
        //LIMIT 起始位置
        val startPos = (curPage - 1) * pageCountSize
        //LIMIT 结束位置
        val endPos = startPos + pageCountSize
        return PageE().apply {
            this.curPage = curPage
            this.dataTotalCount = dataTotalCount
            this.totalPage = pageSize
            this.startPos = startPos
            this.endPos = endPos
        }
    }

    /**
     * 获取ip地址
     */
    fun getIpAddr(request: HttpServletRequest): String? {
        println(request)
        var ipAddress: String?
        try {
            ipAddress = request.getHeader("x-forwarded-for")
            if (ipAddress == null || ipAddress.isEmpty() || UNKNOWN.equals(ipAddress, ignoreCase = true)) {
                ipAddress = request.getHeader("Proxy-Client-IP")
            }
            if (ipAddress == null || ipAddress.isEmpty() || UNKNOWN.equals(ipAddress, ignoreCase = true)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP")
            }
            if (ipAddress == null || ipAddress.isEmpty() || UNKNOWN.equals(ipAddress, ignoreCase = true)) {
                ipAddress = request.remoteAddr
                if (LOCALHOST == ipAddress) {
                    var inet: InetAddress? = null
                    try {
                        inet = InetAddress.getLocalHost()
                    } catch (e: UnknownHostException) {
                        e.printStackTrace()
                    }
                    ipAddress = inet!!.hostAddress
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length()
            if (ipAddress != null && ipAddress.length > 15) {
                if (ipAddress.indexOf(SEPARATOR) > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","))
                }
            }
        } catch (e: Exception) {
            ipAddress = ""
        }
        return ipAddress
    }

    fun getResourcesPath(): String =
            "${ResourceUtils.getURL("classpath:").path}resources"

    /**
     * 返回文件流给客户端
     */
    fun downloadFileToClient(response: HttpServletResponse,fileName: String,filePath: String) {
        val file = File(filePath)
        if (file.exists()) {
            response.setHeader("content-type", "application/octet-stream")
            response.contentType = "application/octet-stream"
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"))
            //文件下载
            val buffer = ByteArray(1024)
            var fis: FileInputStream? = null
            var bis: BufferedInputStream? = null
            try {
                fis = FileInputStream(file)
                bis = BufferedInputStream(fis)
                val os: OutputStream = response.outputStream
                var i = bis.read(buffer)
                while (i != -1) {
                    os.write(buffer, 0, i)
                    i = bis.read(buffer)
                }

            } catch (e: Exception) {

            } finally {
                if (bis != null) {
                    try {
                        bis.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                if (fis != null) {
                    try {
                        fis.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}