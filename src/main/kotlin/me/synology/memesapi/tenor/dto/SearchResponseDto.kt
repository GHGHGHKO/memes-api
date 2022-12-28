package me.synology.memesapi.tenor.dto

data class SearchResponseDto(
    val id: String,
    val title: String,
    val mediaFormats: MediaFormats,
    val contentDescription: String,
    val itemUrl: String)
