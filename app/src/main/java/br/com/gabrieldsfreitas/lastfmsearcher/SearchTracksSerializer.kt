package br.com.gabrieldsfreitas.lastfmsearcher

import com.google.gson.JsonObject

object SearchTracksSerializer {
    fun serializeFromJson(json: JsonObject?): SearchTrackResponse? {
        val searchedTracks: MutableList<SearchedTrack> = arrayListOf()
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

            searchedTracks.add(SearchedTrack(name, artist, url, listeners))
        }

        return SearchTrackResponse(searchedTracks, totalResults)
    }
}