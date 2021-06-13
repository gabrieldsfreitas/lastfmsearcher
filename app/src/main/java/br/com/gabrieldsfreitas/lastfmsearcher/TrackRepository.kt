package br.com.gabrieldsfreitas.lastfmsearcher

import android.util.Log
import androidx.lifecycle.liveData
import java.net.ConnectException

sealed class ApiResult<out R> {
    data class OnSuccess<out T>(val data: T?) : ApiResult<T?>()
    data class OnError(val exception: Exception) : ApiResult<Nothing>()
}

class TrackRepository(private val api: Api) {

    fun searchTrack(wordTyped: String) = liveData {
        try {
            val response = api.searchTrack(wordTyped = wordTyped)
            if (response.isSuccessful) {
                Log.e("#FIND", "#FIND sucess")
                emit(ApiResult.OnSuccess(data = SearchTracksSerializer.serializeFromJson(response.body())))
            } else {
                Log.e("#FIND", "#FIND ERROR")
                emit(ApiResult.OnError(exception = Exception("Fail with code ${response.code()}")))
            }
        } catch (e: ConnectException) {
            Log.e("#FIND", "#FIND EXCE1")
            emit(ApiResult.OnError(exception = Exception("Fail to connect server")))
        } catch (e: Exception) {
            Log.e("#FIND", "#FIND EXCE2")
            emit(ApiResult.OnError(exception = e))
        }
    }

    fun topTracks() = liveData {
        try {
            val response = api.topTracks()
            if (response.isSuccessful) {
                Log.e("#FIND", "#FIND sucess")
                emit(ApiResult.OnSuccess(data = TopTracksSerializer.serializeFromJson(response.body())))
            } else {
                Log.e("#FIND", "#FIND ERROR")
                emit(ApiResult.OnError(exception = Exception("Fail with code ${response.code()}")))
            }
        } catch (e: ConnectException) {
            Log.e("#FIND", "#FIND EXCE1")
            emit(ApiResult.OnError(exception = Exception("Fail to connect server")))
        } catch (e: Exception) {
            Log.e("#FIND", "#FIND EXCE2")
            emit(ApiResult.OnError(exception = e))
        }
    }
}