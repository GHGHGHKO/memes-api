package me.synology.memesapi.common.service.sign

import me.synology.memesapi.common.advice.EmailSignInFailedExceptionCustom
import me.synology.memesapi.common.advice.UserExistExceptionCustom
import me.synology.memesapi.common.advice.UserNotFoundExceptionCustom
import me.synology.memesapi.common.config.security.JwtTokenProvider
import me.synology.memesapi.common.domain.UserMaster
import me.synology.memesapi.common.dto.SignInRequestDto
import me.synology.memesapi.common.dto.SignInResponseDto
import me.synology.memesapi.common.dto.SignUpRequestDto
import me.synology.memesapi.common.repository.UserMasterRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class SignService(
    private val userMasterRepository: UserMasterRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @Value("\${info.api.sign}")
    val apiName: String = ""

    fun signUp(signUpRequestDto: SignUpRequestDto) {
        val user = userMasterRepository.findByEmail(signUpRequestDto.email)

        if (user != null) {
            throw UserExistExceptionCustom()
        }

        val roles = mutableListOf<String>()
        roles.add("ROLE_USER")

        userMasterRepository.save(
            UserMaster(
                email = signUpRequestDto.email,
                password = passwordEncoder.encode(signUpRequestDto.password),
                nickName = signUpRequestDto.nickname,
                createUser = apiName,
                updateUser = apiName,
                roles = roles
            )
        )
    }

    fun signIn(signInRequestDto: SignInRequestDto): SignInResponseDto {
        val user: UserMaster = userMasterRepository.findByEmail(signInRequestDto.email)
            ?: throw UserNotFoundExceptionCustom()

        if (!passwordEncoder.matches(signInRequestDto.password, user.password)) {
            throw EmailSignInFailedExceptionCustom()
        }

        val jwtInfo = jwtTokenProvider.createToken(user.id.toString(), user.roles)

        return SignInResponseDto(
            jwtInfo.token, jwtInfo.utcExpirationDate, user.roles
        )
    }
}
