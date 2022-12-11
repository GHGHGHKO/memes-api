package me.synology.memesapi.blog.repository

import me.synology.memesapi.blog.domain.Board
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface BoardRepository : JpaRepository<Board, UUID> {
}
