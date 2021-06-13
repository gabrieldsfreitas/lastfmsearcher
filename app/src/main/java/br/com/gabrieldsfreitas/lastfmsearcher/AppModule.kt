package br.com.gabrieldsfreitas.lastfmsearcher

import br.com.gabrieldsfreitas.lastfmsearcher.repository.TrackRepository
import br.com.gabrieldsfreitas.lastfmsearcher.ui.viewmodel.TrackViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(Api.URL_BASE)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<OkHttpClient> {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
    single<Api> { get<Retrofit>().create(Api::class.java) }
}

val serviceModule = module {
    single<TrackRepository> { TrackRepository(get()) }
}

val viewModelModule = module {
    viewModel<TrackViewModel> { TrackViewModel(get()) }
}

val appModules = listOf(
    retrofitModule, serviceModule, viewModelModule
)