package me.synology.memesapi.common.config.security

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component("authenticationEntryPointCustom")
class AuthenticationEntryPointCustom(
    @Qualifier(value = "handlerExceptionResolver")
    private val resolver: HandlerExceptionResolver
): AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        resolver.resolveException(request, response, null, authException)
    }
}
