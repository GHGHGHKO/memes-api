package me.synology.memesapi.common.model.response

class SingleResult<T>(
    success: Boolean,
    code: Int,
    message: String,
    var data: T
): CommonResult(success, code, message)
