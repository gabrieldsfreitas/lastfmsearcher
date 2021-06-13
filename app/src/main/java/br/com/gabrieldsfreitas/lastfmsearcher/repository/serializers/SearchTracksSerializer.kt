package br.com.gabrieldsfreitas.lastfmsearcher.repository.serializers

import br.com.gabrieldsfreitas.lastfmsearcher.model.SearchedTracksModel
import br.com.gabrieldsfreitas.lastfmsearcher.model.TrackModel
import com.google.gson.JsonObject

object SearchTracksSerializer {
    fun serializeFromJson(json: JsonObject?): SearchedTracksModel? {
        val trackModels: MutableList<TrackModel> = arrayListOf()
        val totalResults: String

        val resultJson = json?.get("results")?.asJsonObject ?: return null
        totalResults = resultJson.get("opensearch:totalResults").asString ?: ""

        val trackMatchesJson = resultJson.get("trackmatches").asJsonObject ?: return null
        trackMatchesJson.get("track").asJsonArray.forEach { trackJson ->
            val trackJsonObject = trackJson.asJsonObject

            val name = trackJsonObject.get("name").asString
            val artist = trackJsonObject.get("artist").asString
            val url = trackJsonObject.get("url").asString
            val listeners = trackJsonObject.get("listeners").asString

            trackModels.add(TrackModel(name, artist, url, listeners))
        }

        return SearchedTracksModel(trackModels, totalResults)
    }
}