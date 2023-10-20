package id.my.mufidz.mandirinews

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MandiriNews : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                super.log(priority, "My APP $tag", message, t)
            }

            override fun createStackElementTag(element: StackTraceElement): String {
                return String.format(
                    "%s.%s:line %s",
                    super.createStackElementTag(element),
                    element.methodName,
                    element.lineNumber
                )
            }
        })
    }

    override fun newImageLoader(): ImageLoader = ImageLoader(this).newBuilder()
        .memoryCachePolicy(CachePolicy.ENABLED)
        .memoryCache {
            MemoryCache.Builder(this)
                .maxSizePercent(0.1)
                .strongReferencesEnabled(true)
                .build()
        }
        .diskCachePolicy(CachePolicy.ENABLED)
        .diskCache {
            DiskCache.Builder()
                .maxSizePercent(0.1)
                .directory(cacheDir)
                .build()
        }
        .logger(DebugLogger())
        .build()
}