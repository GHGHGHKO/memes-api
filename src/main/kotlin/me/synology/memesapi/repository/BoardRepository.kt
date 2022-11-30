package me.synology.memesapi.repository

import me.synology.memesapi.domain.Board
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface BoardRepository : JpaRepository<Board, UUID> {
}
