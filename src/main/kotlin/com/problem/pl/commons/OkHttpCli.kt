package com.problem.pl.commons

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.apache.commons.lang3.exception.ExceptionUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.annotation.Resource


@Component
class OkHttpCli {

    private val JsonMediaType: MediaType = "application/json; charset=utf-8".toMediaType()
    private val XmlMediaType: MediaType = "application/xml; charset=utf-8".toMediaType()
    private val log = LoggerFactory.getLogger(OkHttpCli::class.java)

    @Resource
    private lateinit var okHttpClient: OkHttpClient

    /**
     * get 请求
     * @param url       请求url地址
     * @return string
     */
    fun doGet(url: String): String {
        return doGet(url, HashMap(), arrayOf())
    }


    /**
     * get 请求
     * @param url       请求url地址
     * @param params    请求参数 map
     * @return string
     */
    fun doGet(url: String, params: Map<String, String>): String {
        return doGet(url, params, arrayOf())
    }

    /**
     * get 请求
     * @param url       请求url地址
     * @param headers   请求头字段 {k1, v1 k2, v2, ...}
     * @return string
     */
    fun doGet(url: String, headers: Array<String>): String {
        return doGet(url, HashMap(), headers)
    }


    /**
     * get 请求
     * @param url       请求url地址
     * @param params    请求参数 map
     * @param headers   请求头字段 {k1, v1 k2, v2, ...}
     * @return string
     */
    fun doGet(url: String, params: Map<String, Any>, headers: Array<String>): String {
        val sb = StringBuilder(url)
        if (params.keys.isNotEmpty()) {
            var firstFlag = true
            for (key in params.keys) {
                if (firstFlag) {
                    sb.append("?").append(key).append("=").append(params[key])
                    firstFlag = false
                } else {
                    sb.append("&").append(key).append("=").append(params[key])
                }
            }
        }
        val builder: Request.Builder = Request.Builder()
        if (headers.isNotEmpty()) {
            if (headers.size % 2 == 0) {
                var i = 0
                while (i < headers.size) {
                    builder.addHeader(headers[i], headers[i + 1])
                    i += 2
                }
            } else {
                log.warn("headers's length[{}] is error.", headers.size)
            }
        }
        val request: Request = builder.url(sb.toString()).build()
        log.info("do get request and url[{}]", sb.toString())
        return execute(request)
    }

    /**
     * post 请求
     * @param url       请求url地址
     * @param params    请求参数 map
     * @return string
     */
    fun doPost(url: String, params: Map<String, String>): String {
        val builder = FormBody.Builder()
        if (params.keys.isNotEmpty()) {
            for (key in params.keys) {
                builder.add(key, params[key] ?: "")
            }
        }
        val request: Request = Request.Builder().url(url).post(builder.build()).build()
        log.info("do post request and url[{}]", url)
        return execute(request)
    }


    /**
     * post 请求, 请求数据为 json 的字符串
     * @param url       请求url地址
     * @param json      请求数据, json 字符串
     * @return string
     */
    fun doPostJson(url: String, json: String): String {
        log.info("do post request and url[{}]", url)
        return exectePost(url, json, JsonMediaType)
    }

    /**
     * post 请求, 请求数据为 xml 的字符串
     * @param url       请求url地址
     * @param xml       请求数据, xml 字符串
     * @return string
     */
    fun doPostXml(url: String, xml: String): String? {
        log.info("do post request and url[{}]", url)
        return exectePost(url, xml, XmlMediaType)
    }


    private fun exectePost(url: String, data: String, contentType: MediaType): String {
        val requestBody: RequestBody = data.toRequestBody(contentType)
        val request: Request = Request.Builder().url(url).post(requestBody).build()
        return execute(request)
    }

    private fun execute(request: Request): String {
        var response: Response? = null
        try {
            response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                return response.body?.string() ?: ""
            }
        } catch (e: Exception) {
            log.error(ExceptionUtils.getStackTrace(e))
        } finally {
            response?.close()
        }
        return ""
    }
}