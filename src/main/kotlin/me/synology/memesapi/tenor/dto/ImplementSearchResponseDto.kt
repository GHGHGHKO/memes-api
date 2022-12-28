package me.synology.memesapi.tenor.dto

import com.google.gson.annotations.SerializedName

data class ImplementSearchResponseDto(
    @SerializedName("results") val results: MutableList<Results>
)

data class Results (
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("media_formats") val mediaFormats: MediaFormats,
    @SerializedName("content_description") val contentDescription: String,
    @SerializedName("itemurl") val itemUrl: String,
)

data class MediaFormats(
    @SerializedName("tinygif") val tinyGif: TinyGif
)

data class TinyGif(
    @SerializedName("url") val url: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("preview") val preview: String,
    @SerializedName("dims") val dims: List<Int>,
    @SerializedName("size") val size: Int,
)
