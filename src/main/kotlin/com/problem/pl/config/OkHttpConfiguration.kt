package com.problem.pl.config

import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


@Configuration
class OkHttpConfiguration {

    @Value("\${ok.http.connect-timeout}")
    private val connectTimeout: Int = 10

    @Value("\${ok.http.read-timeout}")
    private val readTimeout: Int = 10

    @Value("\${ok.http.write-timeout}")
    private val writeTimeout: Int = 10

    @Value("\${ok.http.max-idle-connections}")
    private val maxIdleConnections: Int = 10

    @Value("\${ok.http.keep-alive-duration}")
    private val keepAliveDuration: Long = 300


    @Bean
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory(), x509TrustManager()) // 是否开启缓存
                .retryOnConnectionFailure(false)
                .connectionPool(pool()!!)
                .connectTimeout(connectTimeout.toLong(), TimeUnit.SECONDS)
                .readTimeout(readTimeout.toLong(), TimeUnit.SECONDS)
                .writeTimeout(writeTimeout.toLong(), TimeUnit.SECONDS)
                .hostnameVerifier(HostnameVerifier { hostname: String?, session: SSLSession? -> true }) // 设置代理
                //            	.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)))
                // 拦截器
                //                .addInterceptor()
                .build()
    }

    @Bean
    fun x509TrustManager(): X509TrustManager {
        return object : X509TrustManager {

            override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                return arrayOfNulls(0)
            }
        }
    }

    @Bean
    fun sslSocketFactory(): SSLSocketFactory {
        // 信任任何链接
        val sslContext: SSLContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf<TrustManager>(x509TrustManager()), SecureRandom())
        return sslContext.socketFactory
    }

    @Bean
    fun pool(): ConnectionPool? {
        return ConnectionPool(maxIdleConnections!!, keepAliveDuration!!, TimeUnit.SECONDS)
    }
}