package id.my.mufidz.mandirinews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.jensklingenberg.ktorfit.Ktorfit
import id.my.mufidz.mandirinews.BuildConfig
import id.my.mufidz.mandirinews.data.network.NewsAPIServices
import id.my.mufidz.mandirinews.utils.Const
import id.my.mufidz.mandirinews.utils.NewsApiResponseAdapterFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideBaseHttpClient(): HttpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    coerceInputValues = true

                }, ContentType.Application.Json
            )
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Timber.d(message)
                }
            }
        }
        defaultRequest {
            url(Const.BASE_URL)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header(HttpHeaders.Authorization, BuildConfig.NEWS_APIKEY)

        }
    }

    @Singleton
    @Provides
    fun provideKtorfit(httpClient: HttpClient): Ktorfit =
        Ktorfit.Builder().httpClient(httpClient)
            .converterFactories(NewsApiResponseAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideNewsApi(ktorfit: Ktorfit): NewsAPIServices = ktorfit.create()
}