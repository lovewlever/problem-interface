package com.problem.pl.commons

import com.google.gson.Gson
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter

object JwtCommon {

    //签名秘钥 自定义
    private const val BASE64SECRET = "d303e844c4eddb3b5c7c974a2062d0f3"

    //超时毫秒数（默认`1`天）
    private const val EXPIRESSECOND = 1*24*60*60*1000

    //用于JWT加密的密匙 自定义
    private const val DATAKEY = "4cbda5fddaf0c8cca09fd187a6bcf178"


    /**
     * @Author: Helon
     * @Description: 生成JWT字符串
     * 格式：A.B.C
     * A-header头信息
     * B-payload 有效负荷
     * C-signature 签名信息 是将header和payload进行加密生成的
     * @param userId - 用户编号
     * @param userName - 用户名
     * @param identities - 客户端信息（变长参数），目前包含浏览器信息，用于客户端拦截器校验，防止跨域非法访问
     * @Data: 2018/7/28 19:26
     * @Modified By:
     */
    fun generateJWT(userId: String, userName: String, vararg identities: String): String {

        val signatureAlgorithm = SignatureAlgorithm.HS256
        //获取当前系统时间
        val nowTimeMillis = System.currentTimeMillis()
        val now = Date(nowTimeMillis)
        //将BASE64SECRET常量字符串使用base64解码成字节数组
        val apiKeySecretBytes = DatatypeConverter.parseBase64Binary(BASE64SECRET)
        //使用HmacSHA256签名算法生成一个HS256的签名秘钥Key
        val signingKey = SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.jcaName)

        //添加构成JWT的参数
        val headMap = HashMap<String, Any>()
        /*
            Header
            {
              "alg": "HS256",
              "typ": "JWT"
            }
         */
        headMap["alg"] = SignatureAlgorithm.HS256.value
        headMap["typ"] = "JWT"
        val builder = Jwts.builder().setHeader(headMap)
                /*
                    Payload
                    {
                      "userId": "1234567890",
                      "userName": "John Doe",
                    }
                 */
                //加密后的客户编号
                .claim("userId", AESSecretCommon.encryptToStr(userId, DATAKEY))
                //客户名称
                .claim("userName", userName)
                //客户端浏览器信息
                .claim("userAgent", identities[0])
                //Signature
                .signWith(signatureAlgorithm, signingKey)
        //添加Token过期时间
        if (EXPIRESSECOND >= 0) {
            val expMillis = nowTimeMillis + EXPIRESSECOND
            val expDate = Date(expMillis)
            builder.setExpiration(expDate).setNotBefore(now)
        }
        return builder.compact()

    }

    /**
     * @Author: Helon
     * @Description: 解析JWT
     * 返回Claims对象
     * @param jsonWebToken - JWT
     * @Data: 2018/7/28 19:25
     * @Modified By:
     */
    fun parseJWT(jsonWebToken: String): Claims? {
        var claims: Claims? = null
        try {
            if ("" != jsonWebToken) {
                //解析jwt
                claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(BASE64SECRET))
                        .parseClaimsJws(jsonWebToken).body
            } else {
                //logger.warn("[JWTHelper]-json web token 为空");
            }
        } catch (e: Exception) {
            //logger.error("[JWTHelper]-JWT解析异常：可能因为token已经超时或非法token");
        }

        return claims
    }

    /**
     * @Author: Helon
     * @Description: 校验JWT是否有效
     * 返回json字符串的demo:
     * {"freshToken":"A.B.C","userName":"Judy","userId":"123", "userAgent":"xxxx"}
     * freshToken-刷新后的jwt
     * userName-客户名称
     * userId-客户编号
     * userAgent-客户端浏览器信息
     * @param jsonWebToken - JWT
     * @Data: 2018/7/24 15:28
     * @Modified By:
     */
    fun validateLogin(jsonWebToken: String): String? {
        var retMap: MutableMap<String, Any>? = null
        val claims = parseJWT(jsonWebToken)
        if (claims != null) {
            //解密客户编号
            val decryptUserId = AESSecretCommon.decryptToStr(claims["userId"] as String, DATAKEY)
            retMap = HashMap()
            //加密后的客户编号
            retMap["userId"] = decryptUserId ?: ""
            //客户名称
            retMap["userName"] = claims["userName"].toString()
            //客户端浏览器信息
            retMap["userAgent"] = claims["userAgent"].toString()
            //刷新JWT
            retMap["freshToken"] = generateJWT(decryptUserId
                    ?: "", claims["userName"].toString(), claims["userAgent"].toString(), claims["domainName"].toString())
        } else {
            //logger.warn("[JWTHelper]-JWT解析出claims为空");
        }
        return retMap?.let { Gson().toJson(retMap) }
    }

    /*@JvmStatic
    fun main(args: Array<String>) {
        Thread( Runnable {
            val jsonWebKey = generateJWT("123", "Judy",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36")
            println(jsonWebKey)
            val claims = parseJWT(jsonWebKey)
            println(claims)

            println(validateLogin(jsonWebKey))
        }).start()

    }*/


}