package com.self.problem.probleminterface.interceptor

import com.google.gson.JsonParser
import com.self.problem.probleminterface.commons.GsonCommon
import com.self.problem.probleminterface.commons.JwtCommon
import com.self.problem.probleminterface.commons.ResultCommon
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 登录验证拦截器
 */
class LoginVerificationInterceptor: HandlerInterceptorAdapter() {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        response.contentType = "text/html,charset=utf-8"
        response.characterEncoding = "UTF-8"

        val header = request.getHeader("token")
        header?.let {
            JwtCommon.validateLogin(header)?.let {
                request.setAttribute("uid", JsonParser.parseString(JwtCommon.validateLogin(header)).asJsonObject.get("userId").asInt)
                return true
            } ?: let {
                response.writer.write(
                        GsonCommon.gson.toJson(ResultCommon.generateResult<String>(
                                code = ResultCommon.RESULT_CODE_NOT_LOGIN,msg = "未登录！"))
                        )
                return false
            }
        } ?: let {
            response.writer.write(GsonCommon.gson.toJson(ResultCommon.generateResult<String>(
                    code = ResultCommon.RESULT_CODE_TOKEN_NOT_EMPTY,msg = "Header token can not Empty")))
            return false
        }
    }

    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
        super.postHandle(request, response, handler, modelAndView)
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        super.afterCompletion(request, response, handler, ex)
    }

    override fun afterConcurrentHandlingStarted(request: HttpServletRequest, response: HttpServletResponse, handler: Any) {
        super.afterConcurrentHandlingStarted(request, response, handler)
    }
}