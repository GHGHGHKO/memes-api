package me.synology.memesapi.common.dto.jwt

import java.util.Date

data class TokenResponseDto(
    val token: String,
    val utcExpirationDate: Date
)
