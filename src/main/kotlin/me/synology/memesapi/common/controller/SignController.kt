package me.synology.memesapi.common.controller

import me.synology.memesapi.common.dto.SignInRequestDto
import me.synology.memesapi.common.dto.SignInResponseDto
import me.synology.memesapi.common.dto.SignUpRequestDto
import me.synology.memesapi.common.model.response.CommonResult
import me.synology.memesapi.common.model.response.SingleResult
import me.synology.memesapi.common.service.ResponseService
import me.synology.memesapi.common.service.sign.SignService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sign/v1")
class SignController(
    private val signService: SignService,
    private val responseService: ResponseService) {

    @PostMapping("/signUp")
    fun signUp(@Validated @RequestBody signUpRequestDto: SignUpRequestDto) :
            ResponseEntity<CommonResult> {
        signService.signUp(signUpRequestDto)
        return ResponseEntity.ok()
            .body(responseService.successResult())
    }

    @PostMapping("/signIn")
    fun signIn(@Validated @RequestBody signInRequestDto: SignInRequestDto):
            ResponseEntity<SingleResult<SignInResponseDto>> {
        return ResponseEntity.ok()
            .body(responseService.singleResult(
                signService.signIn(signInRequestDto)
            ))
    }
}
