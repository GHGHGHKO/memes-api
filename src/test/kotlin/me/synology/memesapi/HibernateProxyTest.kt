package me.synology.memesapi

import me.synology.memesapi.blog.domain.Board
import me.synology.memesapi.blog.domain.BoardInformation
import me.synology.memesapi.blog.domain.User
import me.synology.memesapi.blog.repository.BoardRepository
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class HibernateProxyTest(
    @Autowired private val entityManager: TestEntityManager,
    @Autowired private val boardRepository: BoardRepository) {

    @Test
    fun test() {
        val user = User("홍길동")
        val boardInformation = BoardInformation(null, 1)
        val board = Board("게시판1", "내용1", boardInformation, user, setOf())

        entityManager.persist(user)
        entityManager.persist(board)
        entityManager.flush()
        entityManager.clear()

        val actual = boardRepository.findById(board.id).get()
        println(actual::class)
        println(user::class)

        assertTrue(user == actual.writer)
    }
}
