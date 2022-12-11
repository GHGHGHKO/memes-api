package me.synology.memesapi.common.service.sign

import me.synology.memesapi.common.advice.UserExistExceptionCustom
import me.synology.memesapi.common.advice.UserNotFoundExceptionCustom
import me.synology.memesapi.common.config.JwtTokenProvider
import me.synology.memesapi.common.domain.UserMaster
import me.synology.memesapi.common.dto.SignupRequestDto
import me.synology.memesapi.common.repository.UserMasterRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class SignService(
    private val userMasterRepository: UserMasterRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider) {

    @Value("\${info.api.sign}")
    val apiName: String = ""

    fun signUp(signupRequestDto: SignupRequestDto) {
        val user = userMasterRepository.findByEmail(signupRequestDto.email)

        if (user != null) {
            throw UserExistExceptionCustom()
        }

        userMasterRepository.save(
            UserMaster(
                email = signupRequestDto.email,
                password = signupRequestDto.password,
                nickName = signupRequestDto.nickname,
                createUser = apiName,
                updateUser = apiName
            )
        )
    }
}
