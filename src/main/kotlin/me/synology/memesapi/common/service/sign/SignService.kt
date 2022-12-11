package me.synology.memesapi.common.service.sign

import me.synology.memesapi.common.advice.UserExistExceptionCustom
import me.synology.memesapi.common.config.JwtTokenProvider
import me.synology.memesapi.common.domain.UserMaster
import me.synology.memesapi.common.dto.SignUpRequestDto
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

    fun signUp(signUpRequestDto: SignUpRequestDto) {
        val user = userMasterRepository.findByEmail(signUpRequestDto.email)

        if (user != null) {
            throw UserExistExceptionCustom()
        }

        userMasterRepository.save(
            UserMaster(
                email = signUpRequestDto.email,
                password = signUpRequestDto.password,
                nickName = signUpRequestDto.nickname,
                createUser = apiName,
                updateUser = apiName
            )
        )
    }
}
