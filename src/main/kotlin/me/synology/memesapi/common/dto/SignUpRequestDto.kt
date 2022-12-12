package me.synology.memesapi.common.dto

import javax.validation.constraints.Email

data class SignUpRequestDto(
    @Email
    var email: String,
    var password: String,
    var nickname: String
    )
