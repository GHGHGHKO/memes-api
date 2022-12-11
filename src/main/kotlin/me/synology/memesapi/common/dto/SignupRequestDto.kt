package me.synology.memesapi.common.dto

import javax.validation.constraints.Email

data class SignupRequestDto(
    @Email
    val email: String,
    val password: String,
    val nickname: String
    )
