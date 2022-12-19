package me.synology.memesapi.common.advice

sealed interface Error

sealed class IOError : Error, RuntimeException {

    constructor(message: String, throwable: Throwable): super(message, throwable)
    constructor(message: String): super(message)
    constructor(): super()
}

class EmailSignInFailedExceptionCustom : IOError()
class UserExistExceptionCustom : IOError()
class UserNotFoundExceptionCustom : IOError()
