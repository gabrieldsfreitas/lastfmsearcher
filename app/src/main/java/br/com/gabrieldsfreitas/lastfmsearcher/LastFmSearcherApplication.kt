package br.com.gabrieldsfreitas.lastfmsearcher

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LastFmSearcherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LastFmSearcherApplication)
            modules(appModules)
        }
    }
}