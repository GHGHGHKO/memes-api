package me.synology.memesapi.common.advice

import me.synology.memesapi.common.model.response.CommonResult
import me.synology.memesapi.common.service.ResponseService
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class ExceptionAdvice(
    private val responseService: ResponseService,
    private val messageSource: MessageSource) {

    @ExceptionHandler(value = [Exception::class])
    @ResponseBody
    fun defaultException(request: HttpServletRequest, e: Exception): ResponseEntity<CommonResult> {
        // TODO logger
        println("Exception endPoint: ${request.method} ${request.requestURI}, " +
                "queryString : ${request.queryString}, " +
                "exception : $e")

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(responseService.failResult(
                getMessage("unKnown.code").toInt(),
                getMessage("unKnown.message")))
    }

    private fun getMessage(code: String): String {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale())
    }
}