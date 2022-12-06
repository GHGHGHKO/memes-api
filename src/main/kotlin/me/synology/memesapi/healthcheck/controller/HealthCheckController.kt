package me.synology.memesapi.healthcheck.controller

import me.synology.memesapi.common.model.response.CommonResult
import me.synology.memesapi.common.service.ResponseService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/healthCheck/v1"])
class HealthCheckController(
    private val responseService: ResponseService
) {

    @GetMapping
    private fun healthCheck(): ResponseEntity<CommonResult> {
        return ResponseEntity.ok()
            .body(responseService.successResult())
    }
}
