package br.com.gabrieldsfreitas.lastfmsearcher.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackModel(
    val name: String,
    val artist: String,
    val url: String,
    val listeners: String,
) : Parcelable