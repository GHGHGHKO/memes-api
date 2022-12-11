package me.synology.memesapi.common.advice

class UserNotFoundExceptionCustom : RuntimeException {

    constructor(message: String, throwable: Throwable): super(message, throwable)
    constructor(message: String): super(message)
    constructor(): super()
}
