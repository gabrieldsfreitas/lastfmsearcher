package br.com.gabrieldsfreitas.lastfmsearcher

import br.com.gabrieldsfreitas.lastfmsearcher.repository.serializers.TopTracksSerializer
import org.junit.Assert
import org.junit.Test

class TopTracksSerializerTest : BaseTest() {
    @Test
    fun testSerializeTopTrackResponse() {
        val json = fromFile("TopTracksResponse")
        val searchedTracksModel = TopTracksSerializer.serializeFromJson(json)
        Assert.assertNotNull(searchedTracksModel)

        Assert.assertEquals(searchedTracksModel?.totalResults, "3")

        val track1 = searchedTracksModel?.trackModels?.first()
        Assert.assertEquals(track1?.name, "good 4 u")
        Assert.assertEquals(track1?.artist, "Olivia Rodrigo")
        Assert.assertEquals(track1?.listeners, "336298")
        Assert.assertEquals(track1?.url, "https://www.last.fm/music/Olivia+Rodrigo/_/good+4+u")
    }
}