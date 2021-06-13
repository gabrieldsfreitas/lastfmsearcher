package br.com.gabrieldsfreitas.lastfmsearcher.repository

import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.liveData
import br.com.gabrieldsfreitas.lastfmsearcher.Api
import br.com.gabrieldsfreitas.lastfmsearcher.model.SearchedTracksModel
import br.com.gabrieldsfreitas.lastfmsearcher.repository.serializers.SearchTracksSerializer
import br.com.gabrieldsfreitas.lastfmsearcher.repository.serializers.TopTracksSerializer

sealed class ApiResult<out R> {
    data class OnSuccess<out T>(val data: T) : ApiResult<T>()
    data class OnError(val exception: Exception) : ApiResult<Nothing>()
}

class TrackRepository(private val api: Api) {

    fun searchTrack(wordTyped: String) = liveData {
        try {
            val response = api.searchTrack(wordTyped = wordTyped)
            if (response.isSuccessful) {
                SearchTracksSerializer.serializeFromJson(response.body())?.let { searchedTracksModel ->
                    onResponse(searchedTracksModel)
                } ?: onInvalidResponse()

            } else {
                onSomethingWentWrong()
            }
        } catch (e: Exception) {
            onSomethingWentWrong()
        }
    }

    fun topTracks() = liveData {
        try {
            val response = api.topTracks()
            if (response.isSuccessful) {
                TopTracksSerializer.serializeFromJson(response.body())?.let { searchedTracksModel ->
                    onResponse(searchedTracksModel)
                } ?: onInvalidResponse()

            } else {
                onSomethingWentWrong()
            }
        } catch (e: Exception) {
            onSomethingWentWrong()
        }
    }

    private suspend fun LiveDataScope<ApiResult<SearchedTracksModel>>.onResponse(model: SearchedTracksModel) {
        emit(ApiResult.OnSuccess(data = model))
    }

    private suspend fun LiveDataScope<ApiResult<SearchedTracksModel>>.onSomethingWentWrong() {
        emit(ApiResult.OnError(exception = Exception("Something went wrong")))
    }

    private suspend fun LiveDataScope<ApiResult<SearchedTracksModel>>.onInvalidResponse() {
        emit(ApiResult.OnError(exception = Exception("Invalid response")))
    }
}