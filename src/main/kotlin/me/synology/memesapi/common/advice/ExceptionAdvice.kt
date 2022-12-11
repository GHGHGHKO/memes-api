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

    @ExceptionHandler(value = [UserNotFoundExceptionCustom::class])
    @ResponseBody
    fun userNotFoundException(): ResponseEntity<CommonResult> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(responseService.failResult(
                getMessage("userNotFound.code").toInt(),
                getMessage("userNotFound.message")))
    }

    @ExceptionHandler(value = [UserExistExceptionCustom::class])
    @ResponseBody
    fun userExistException(): ResponseEntity<CommonResult> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(responseService.failResult(
                getMessage("existingUser.code").toInt(),
                getMessage("existingUser.message")))
    }

    @ExceptionHandler(value = [EmailSignInFailedExceptionCustom::class])
    @ResponseBody
    fun emailSignInFailed(): ResponseEntity<CommonResult> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(responseService.failResult(
                getMessage("emailSignInFailed.code").toInt(),
                getMessage("emailSignInFailed.message")))
    }

    private fun getMessage(code: String): String {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale())
    }
}
