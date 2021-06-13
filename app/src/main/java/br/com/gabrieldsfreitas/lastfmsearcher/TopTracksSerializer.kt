package br.com.gabrieldsfreitas.lastfmsearcher

import com.google.gson.JsonObject

object TopTracksSerializer {
    fun serializeFromJson(json: JsonObject?): SearchTrackResponse? {
        val searchedTracks: MutableList<SearchedTrack> = arrayListOf()
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

            searchedTracks.add(SearchedTrack(name, artist, url, listeners))
        }

        return SearchTrackResponse(searchedTracks, totalResults)
    }
}