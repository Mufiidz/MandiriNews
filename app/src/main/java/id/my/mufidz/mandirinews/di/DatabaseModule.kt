package id.my.mufidz.mandirinews.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.my.mufidz.mandirinews.data.local.NewsDatabase
import id.my.mufidz.mandirinews.data.local.SearchDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME: String = "mandirinews"

    @Provides
    @Singleton
    fun providesNewsDatabase(application: Application): NewsDatabase =
        Room.databaseBuilder(application, NewsDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providesSearchNewsDao(newsDatabase: NewsDatabase): SearchDao = newsDatabase.searchDao()
}