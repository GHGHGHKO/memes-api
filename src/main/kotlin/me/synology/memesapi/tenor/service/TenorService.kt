package me.synology.memesapi.tenor.service

import me.synology.memesapi.common.advice.UserNotFoundExceptionCustom
import me.synology.memesapi.tenor.`interface`.TenorApi
import me.synology.memesapi.tenor.dto.ImplementSearchResponseDto
import me.synology.memesapi.tenor.dto.SearchResponseDto
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Service
class TenorService(
    private val client: OkHttpClient
) {

    @Value("\${tenor.key}")
    lateinit var tenorKey: String

    @Value("\${tenor.url}")
    lateinit var tenorUrl: String

    fun implementSearch(search: String): MutableList<SearchResponseDto> {
        val httpClient = client.newBuilder().build()

        val tenorRetrofit = Retrofit.Builder()
            .baseUrl(tenorUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        val callTenor: Call<ImplementSearchResponseDto> = tenorRetrofit
            .create(TenorApi::class.java)
            .implementSearch(search, tenorKey)
        try {
            val tenorResponse: Response<ImplementSearchResponseDto> = callTenor
                .execute()

            val searchResponse: MutableList<SearchResponseDto> = mutableListOf()

            if (tenorResponse.errorBody() != null) {
                println("${tenorResponse.errorBody()}")
            }

            tenorResponse.body()?.let {
                tenorResponse.body()!!
                    .results.map {
                            results ->
                        val searchResponseDto = SearchResponseDto(
                            results.id, results.title, results.mediaFormat,
                            results.contentDescription, results.itemUrl)
                        searchResponse.add(searchResponseDto)
                }
            } ?: throw Exception()

            return searchResponse

        } catch (e: Exception) {
            println("tenor implementSearch Exception : $e")
            throw Exception()
        }
    }
}
