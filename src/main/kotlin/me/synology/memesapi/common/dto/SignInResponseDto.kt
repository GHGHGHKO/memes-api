package me.synology.memesapi.common.dto

import java.util.Date

data class SignInResponseDto(
    val token: String,
    val utcExpirationDate: Date,
    val roles: MutableList<String>
)
