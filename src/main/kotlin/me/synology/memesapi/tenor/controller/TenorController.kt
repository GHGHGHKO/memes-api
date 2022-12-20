package me.synology.memesapi.tenor.controller

import me.synology.memesapi.common.model.response.MutableListResult
import me.synology.memesapi.common.service.ResponseService
import me.synology.memesapi.tenor.dto.SearchResponseDto
import me.synology.memesapi.tenor.service.TenorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/tenor/v1")
@RestController
class TenorController(
    private val tenorService: TenorService,
    private val responseService: ResponseService
) {

    @GetMapping("/search")
    fun search(
        @RequestParam(required = true) search: String
    ): ResponseEntity<MutableListResult<SearchResponseDto>> {
        return ResponseEntity.ok()
            .body(responseService.mutableListResult(
                tenorService.implementSearch(search)
            ))
    }
}
