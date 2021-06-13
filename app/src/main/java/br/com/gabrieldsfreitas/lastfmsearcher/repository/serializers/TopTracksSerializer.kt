package br.com.gabrieldsfreitas.lastfmsearcher.repository.serializers

import br.com.gabrieldsfreitas.lastfmsearcher.model.SearchedTracksModel
import br.com.gabrieldsfreitas.lastfmsearcher.model.TrackModel
import com.google.gson.JsonObject

object TopTracksSerializer {
    fun serializeFromJson(json: JsonObject?): SearchedTracksModel? {
        val trackModels: MutableList<TrackModel> = arrayListOf()
        val totalResults: String

        val resultJson = json?.get("tracks")?.asJsonObject ?: return null
        val tracks = resultJson.get("track").asJsonArray
        totalResults = tracks.size().toString()
        tracks.forEach { trackJson ->
            val trackJsonObject = trackJson.asJsonObject

            val name = trackJsonObject.get("name").asString
            val artist = trackJsonObject.get("artist").asJsonObject.get("name").asString
            val url = trackJsonObject.get("url").asString
            val listeners = trackJsonObject.get("listeners").asString

            trackModels.add(TrackModel(name, artist, url, listeners))
        }

        return SearchedTracksModel(trackModels, totalResults)
    }
}