package me.synology.memesapi.tenor.controller

import me.synology.memesapi.common.dto.SignInRequestDto
import me.synology.memesapi.common.dto.SignUpRequestDto
import me.synology.memesapi.common.repository.UserMasterRepository
import me.synology.memesapi.common.service.sign.SignService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TenorControllerTest(
    @Autowired
    val mockMvc: MockMvc,

    @Autowired
    private val signService: SignService,

    @Autowired
    private val userMasterRepository: UserMasterRepository
) {

    lateinit var token: String

    @BeforeEach
    fun setUp() {
        val email = "test-memes@tenor.com"
        val password = "rick-rolled!"

        val signUpRequestDto = SignUpRequestDto(
            email = email,
            password = password,
            nickname = "Rick-Astley"
        )

        signService.signUp(signUpRequestDto)

        val signInRequestDto = SignInRequestDto(
            email = email,
            password = password
        )

        token = signService.signIn(signInRequestDto).token
    }

    @AfterEach
    fun tearDown() {
        userMasterRepository.deleteAll()
    }

    @Test
    fun search() {
        mockMvc.perform(get("/tenor/v1?search=excited")
            .header("X-AUTH-TOKEN", token))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.code").value(0))
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.results").isArray)
    }

    @Test
    fun `weird search`() {
        mockMvc.perform(get("/tenor/v1?search=excaertghdryh4ag5yited")
            .header("X-AUTH-TOKEN", token))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.code").value(0))
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.results").isArray)
    }

    @Test
    fun `search without token`() {
        mockMvc.perform(get("/tenor/v1?search=hellopepe"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.code").value(0))
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.results").isArray)
    }
}
