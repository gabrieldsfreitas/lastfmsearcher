package br.com.gabrieldsfreitas.lastfmsearcher

data class SearchTrackResponse(
    val tracks: MutableList<SearchedTrack>,
    val totalResults: String
)

