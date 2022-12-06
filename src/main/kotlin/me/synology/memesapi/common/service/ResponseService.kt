package me.synology.memesapi.common.service

import me.synology.memesapi.common.model.response.CommonResult
import org.springframework.stereotype.Service

@Service
class ResponseService {

    private enum class CommonResponse(
        val code: Int,
        val message: String

    ) {
        FAIL(-1, "failed!")
    }

    fun setFailResult(commonResult: CommonResult) {
        commonResult.success = false
        commonResult.code = CommonResponse.FAIL.code
        commonResult.message = CommonResponse.FAIL.message
    }

    fun successResult(): CommonResult {
        return CommonResult()
    }

    fun failResult(code: Int, message: String): CommonResult {
        val commonResult = CommonResult()
        commonResult.success = false
        commonResult.code = code
        commonResult.message = message
        return commonResult
    }
}
