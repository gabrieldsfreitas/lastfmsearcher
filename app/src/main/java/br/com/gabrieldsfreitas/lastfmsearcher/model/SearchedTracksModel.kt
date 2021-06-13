package br.com.gabrieldsfreitas.lastfmsearcher.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchedTracksModel(
    val trackModels: MutableList<TrackModel>,
    val totalResults: String
) : Parcelable