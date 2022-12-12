package me.synology.memesapi.common.model.response

class ValidationCheckResult(
    success: Boolean,
    code: Int,
    message: String,
    val argumentsNotValid: MutableList<FieldErrors>,
) : CommonResult(success, code, message) {

    class FieldErrors(
        val field: String,
        val value: String,
        val reason: String?
    )
}
