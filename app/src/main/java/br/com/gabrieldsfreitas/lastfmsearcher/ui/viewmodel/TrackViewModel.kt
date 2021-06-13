package br.com.gabrieldsfreitas.lastfmsearcher.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import br.com.gabrieldsfreitas.lastfmsearcher.model.SearchedTracksModel
import br.com.gabrieldsfreitas.lastfmsearcher.repository.ApiResult
import br.com.gabrieldsfreitas.lastfmsearcher.repository.TrackRepository

class TrackViewModel(private val repository: TrackRepository) : ViewModel() {

    fun searchTrack(wordTyped: String): LiveData<ApiResult<SearchedTracksModel>> = repository.searchTrack(wordTyped)

    fun topTracks(): LiveData<ApiResult<SearchedTracksModel>> = repository.topTracks()
}