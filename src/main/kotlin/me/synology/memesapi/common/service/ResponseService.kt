package me.synology.memesapi.common.service

import me.synology.memesapi.common.model.response.CommonResult
import me.synology.memesapi.common.model.response.SingleResult
import me.synology.memesapi.common.model.response.ValidationCheckResult
import org.springframework.stereotype.Service

@Service
class ResponseService {

    fun successResult(): CommonResult {
        return CommonResult()
    }

    fun failResult(code: Int, message: String): CommonResult {
        return CommonResult(false, code, message)
    }

    fun argumentsFailResults(
        code: Int,
        message: String,
        allErrors: MutableList<ValidationCheckResult.FieldErrors>
    ): ValidationCheckResult {
        return ValidationCheckResult(false, code, message, allErrors)
    }

    fun <T> singleResult(data: T): SingleResult<T> {
        val common = successResult()
        return SingleResult(
            common.success,
            common.code,
            common.message,
            data)
    }
}
