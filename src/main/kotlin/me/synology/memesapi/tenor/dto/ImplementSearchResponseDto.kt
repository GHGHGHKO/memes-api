package me.synology.memesapi.tenor.dto

data class ImplementSearchResponseDto(
    val results: MutableList<Results>
)

data class Results (
    val id: String,
    val title: String,
    val mediaFormat: MediaFormat,
    val contentDescription: String,
    val itemUrl: String
)

data class MediaFormat(
    val mediumGif: MediumGif
)

data class MediumGif(
    val common: Common
)

data class Common(
    val url: String,
    val duration: Int,
    val preview: String,
    val dims: List<Int>,
    val size: Int
)
