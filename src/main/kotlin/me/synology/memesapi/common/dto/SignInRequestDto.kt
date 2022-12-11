package me.synology.memesapi.common.dto

import javax.validation.constraints.Email

data class SignInRequestDto(
    @Email
    val email: String,
    val password: String
)
