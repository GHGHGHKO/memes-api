package me.synology.memesapi.tenor.`interface`

import me.synology.memesapi.tenor.dto.ImplementSearchResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TenorApi {

    @GET("/v2/search")
    fun implementSearch(
        @Query("q") searchString: String,
        @Query("key") apiKey: String,
        @Query("limit") limit: Int = 8,
        @Query("random") random: Boolean = true
    ): Call<ImplementSearchResponseDto>
}
