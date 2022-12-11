package me.synology.memesapi.common.service.security

import me.synology.memesapi.common.advice.UserNotFoundExceptionCustom
import me.synology.memesapi.common.repository.UserMasterRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserDetailServiceCustom(
    private val userMasterRepository: UserMasterRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        return userMasterRepository.findById(UUID.fromString(username))
            .orElseThrow { UserNotFoundExceptionCustom() }
    }
}
