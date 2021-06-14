package br.com.gabrieldsfreitas.lastfmsearcher

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.junit.Assert
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.mockito.Mockito
import java.io.File

abstract class BaseTest : KoinTest {

//    @Before
//    fun before() {
//        startKoin {
//            (Mockito.mock(Context::class.java))
//            modules(appModules)
//        }
//    }

    protected fun fromFile(name: String): JsonObject {
        val path = "./src/test/java/br/com/gabrieldsfreitas/lastfmsearcher/$name.json"
        val jsonString: String = File(path).readText(Charsets.UTF_8)

        assert(jsonString.isNotEmpty())

        val json = Gson().fromJson(jsonString, JsonObject::class.java)
        Assert.assertNotNull(json)

        return json
    }
}