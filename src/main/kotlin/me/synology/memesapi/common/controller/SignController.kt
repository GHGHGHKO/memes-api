package me.synology.memesapi.common.controller

import me.synology.memesapi.common.dto.SignupRequestDto
import me.synology.memesapi.common.model.response.CommonResult
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
    fun signIn(@Validated @RequestBody signupRequestDto: SignupRequestDto) :
            ResponseEntity<CommonResult> {
        signService.signUp(signupRequestDto)
        return ResponseEntity.ok()
            .body(responseService.successResult())
    }
}
