package me.synology.memesapi.common.controller

import com.fasterxml.jackson.databind.ObjectMapper
import me.synology.memesapi.common.domain.UserMaster
import me.synology.memesapi.common.dto.SignInRequestDto
import me.synology.memesapi.common.dto.SignUpRequestDto
import me.synology.memesapi.common.repository.UserMasterRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SignControllerTest(
    @Autowired
    val mockMvc: MockMvc,

    @Autowired
    val userMasterRepository: UserMasterRepository,

    @Autowired
    val objectMapper: ObjectMapper,

    @Autowired
    val passwordEncoder: PasswordEncoder
) {

    @BeforeEach
    fun setUp() {
        val roles = mutableListOf<String>()
        roles.add("ROLE_USER")

        userMasterRepository.save(UserMaster(
            email = "goose-duck@gmail.com",
            password = passwordEncoder.encode("Honkhonk1122!"),
            nickName = "goose",
            createUser = "SignControllerTest",
            updateUser = "SignControllerTest",
            roles = roles
        ))
    }

    @AfterEach
    fun tearDown() {
        userMasterRepository.deleteAll()
    }

    @Test
    fun `success signUp`() {
        val signUpRequestDto = SignUpRequestDto(
            "goose@gmail.com",
            "23re34rt!@#",
            "duck")
        mockMvc.perform(post("/sign/v1/signUp")
            .content(objectMapper.writeValueAsString(signUpRequestDto))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.code").value(0))
            .andExpect(jsonPath("$.message").exists())
    }

    @Test
    fun `UserExistExceptionCustom signUp`() {
        val signUpRequestDto = SignUpRequestDto(
            "goose-duck@gmail.com",
            "23re34rt!@#",
            "duck")
        mockMvc.perform(post("/sign/v1/signUp")
            .content(objectMapper.writeValueAsString(signUpRequestDto))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.code").value(-1005))
            .andExpect(jsonPath("$.message").exists())
    }

    @Test
    fun `invalid email signUp`() {
        val signUpRequestDto = SignUpRequestDto(
            "goose-duckgmailcom",
            "",
            "duck")
        mockMvc.perform(post("/sign/v1/signUp")
            .content(objectMapper.writeValueAsString(signUpRequestDto))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.code").value(-1006))
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.argumentsNotValid").isArray)
    }

    @Test
    fun `success signIn`() {
        val signInRequestDto = SignInRequestDto(
            "goose-duck@gmail.com", "Honkhonk1122!")

        mockMvc.perform(post("/sign/v1/signIn")
            .content(objectMapper.writeValueAsString(signInRequestDto))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.code").value(0))
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.token").exists())
            .andExpect(jsonPath("$.data.utcExpirationDate").exists())
            .andExpect(jsonPath("$.data.roles").isNotEmpty)
    }

    @Test
    fun `UserNotFoundExceptionCustom signIn`() {
        val signInRequestDto = SignInRequestDto(
            "duck@gmail.com", "Honkhonk1122!")

        mockMvc.perform(post("/sign/v1/signIn")
            .content(objectMapper.writeValueAsString(signInRequestDto))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.code").value(-1000))
            .andExpect(jsonPath("$.message").exists())
    }

    @Test
    fun `EmailSignInFailedExceptionCustom signIn`() {
        val signInRequestDto = SignInRequestDto(
            "goose-duck@gmail.com", "Honkhonk1122")

        mockMvc.perform(post("/sign/v1/signIn")
            .content(objectMapper.writeValueAsString(signInRequestDto))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.code").value(-1001))
            .andExpect(jsonPath("$.message").exists())
    }
}
