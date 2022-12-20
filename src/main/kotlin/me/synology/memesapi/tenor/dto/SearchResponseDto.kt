package me.synology.memesapi.tenor.dto

data class SearchResponseDto(
    val id: String,
    val title: String,
    val mediaFormat: MediaFormat,
    val contentDescription: String,
    val itemUrl: String)
