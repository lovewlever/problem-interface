package com.self.problem.probleminterface.commons

import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec

/**
 * AES加密工具类
 */
object AESSecretCommon {
    /**秘钥的大小 */
    private const val KEYSIZE = 128

    /**
     * @Author: Helon
     * @Description: AES加密
     * @param data - 待加密内容
     * @param key - 加密秘钥
     * @Data: 2018/7/28 18:42
     * @Modified By:
     */
    fun encrypt(data: String, key: String): ByteArray? {
        if ("" != data) {
            try {
                val keyGenerator = KeyGenerator.getInstance("AES")
                //选择一种固定算法，为了避免不同java实现的不同算法，生成不同的密钥，而导致解密失败
                val random = SecureRandom.getInstance("SHA1PRNG")
                random.setSeed(key.toByteArray())
                keyGenerator.init(KEYSIZE, random)
                val secretKey = keyGenerator.generateKey()
                val enCodeFormat = secretKey.encoded
                val secretKeySpec = SecretKeySpec(enCodeFormat, "AES")
                val cipher = Cipher.getInstance("AES") // 创建密码器
                val byteContent = data.toByteArray(charset("utf-8"))
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec) // 初始化
                return cipher.doFinal(byteContent) // 加密
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    /**
     * @Author: Helon
     * @Description: AES加密，返回String
     * @param data - 待加密内容
     * @param key - 加密秘钥
     * @Data: 2018/7/28 18:59
     * @Modified By:
     */
    fun encryptToStr(data: String, key: String): String? {
        return if ("" != data) parseByte2HexStr(encrypt(data, key)) else null
    }

    /**
     * @Author: Helon
     * @Description: AES解密
     * @param data - 待解密字节数组
     * @param key - 秘钥
     * @Data: 2018/7/28 19:01
     * @Modified By:
     */
    fun decrypt(data: ByteArray?, key: String): ByteArray? {
        if (data!!.size > 0) {
            try {
                val keyGenerator = KeyGenerator.getInstance("AES")
                //选择一种固定算法，为了避免不同java实现的不同算法，生成不同的密钥，而导致解密失败
                val random = SecureRandom.getInstance("SHA1PRNG")
                random.setSeed(key.toByteArray())
                keyGenerator.init(KEYSIZE, random)
                val secretKey = keyGenerator.generateKey()
                val enCodeFormat = secretKey.encoded
                val secretKeySpec = SecretKeySpec(enCodeFormat, "AES")
                val cipher = Cipher.getInstance("AES") // 创建密码器
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec) // 初始化
                return cipher.doFinal(data) // 加密
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    /**
     * @Author: Helon
     * @Description: AES解密，返回String
     * @param enCryptdata - 待解密字节数组
     * @param key - 秘钥
     * @Data: 2018/7/28 19:01
     * @Modified By:
     */
    fun decryptToStr(enCryptdata: String, key: String): String? {
        return if ("" != enCryptdata) String(decrypt(parseHexStr2Byte(enCryptdata), key)!!) else null
    }

    /**
     * @Author: Helon
     * @Description: 将二进制转换成16进制
     * @param buf - 二进制数组
     * @Data: 2018/7/28 19:12
     * @Modified By:
     */
    fun parseByte2HexStr(buf: ByteArray?): String {
        val sb = StringBuffer()
        for (i in buf!!.indices) {
            var hex = Integer.toHexString((buf[i].toInt() and 0xFF))
            if (hex.length == 1) {
                hex = "0$hex"
            }
            sb.append(hex.toUpperCase())
        }

        return sb.toString()
    }

    /**
     * @Author: Helon
     * @Description: 将16进制转换为二进制
     * @param hexStr - 16进制字符串
     * @Data: 2018/7/28 19:13
     * @Modified By:
     */
    fun parseHexStr2Byte(hexStr: String): ByteArray? {
        if (hexStr.length < 1) return null
        val result = ByteArray(hexStr.length / 2)
        for (i in 0 until hexStr.length / 2) {
            val high = hexStr.substring(i * 2, i * 2 + 1).toInt(16)
            val low = hexStr.substring(i * 2 + 1, i * 2 + 2).toInt(16)
            result[i] = (high * 16 + low).toByte()
        }
        return result
    }
}