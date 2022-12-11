package me.synology.memesapi.common.repository;

import me.synology.memesapi.common.domain.UserMaster
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserMasterRepository : JpaRepository<UserMaster, UUID>{
    fun findByEmail(email: String): UserMaster?
}
