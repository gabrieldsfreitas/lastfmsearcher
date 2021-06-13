package br.com.gabrieldsfreitas.lastfmsearcher.ui.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.gabrieldsfreitas.lastfmsearcher.ApiResult
import br.com.gabrieldsfreitas.lastfmsearcher.SearchTrackResponse
import br.com.gabrieldsfreitas.lastfmsearcher.TrackRepository

class TrackViewModel(private val repository: TrackRepository) : ViewModel() {

    fun searchTrack(wordTyped: String): LiveData<ApiResult<SearchTrackResponse?>> = repository.searchTrack(wordTyped)

    fun topTracks(): LiveData<ApiResult<SearchTrackResponse?>> = repository.topTracks()
}