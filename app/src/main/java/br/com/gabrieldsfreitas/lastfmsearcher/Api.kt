package br.com.gabrieldsfreitas.lastfmsearcher

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    companion object {
        const val URL_BASE = "https://ws.audioscrobbler.com"
        const val apiKey: String = "4a8ed5ed780581d733cb006b7c145b24"
        const val apiVersion: String = "/2.0/"
        const val topTrackMethod: String = "chart.gettoptracks"
        const val searchTrackMethod: String = "track.search"
        const val searchTracksLimit: String = "50"
        const val topTracksLimit: String = "20"
        const val responseFormat: String = "json"
    }

    @GET(apiVersion)
    suspend fun searchTrack(
        @Query("api_key") key: String? = apiKey,
        @Query("method") method: String? = searchTrackMethod,
        @Query("track") wordTyped: String,
        @Query("format") format: String? = responseFormat,
        @Query("limit") limit: String? = searchTracksLimit,
    ): Response<JsonObject>

    @GET(apiVersion)
    suspend fun topTracks(
        @Query("api_key") key: String? = apiKey,
        @Query("method") method: String? = topTrackMethod,
        @Query("format") format: String? = responseFormat,
        @Query("limit") limit: String? = topTracksLimit,
    ): Response<JsonObject>
}