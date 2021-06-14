package br.com.gabrieldsfreitas.lastfmsearcher

import br.com.gabrieldsfreitas.lastfmsearcher.repository.ApiResult
import br.com.gabrieldsfreitas.lastfmsearcher.ui.viewmodel.TrackViewModel
import org.junit.Test
import org.koin.test.inject
import org.mockito.Mockito

class HomeActivityTest : BaseTest() {
    private val trackViewModel: TrackViewModel by inject()

//    @Test
//    fun shouldShowListWhenTopTracksLoad() {
//        Mockito.`when`(trackViewModel.topTracks()).
//
//        trackViewModel.topTracks().observeForever { result ->
//            when (result) {
//                is ApiResult.OnSuccess -> {
//
//                }
//
//                is ApiResult.OnError -> {
//
//                }
//            }
//        }
//    }
}