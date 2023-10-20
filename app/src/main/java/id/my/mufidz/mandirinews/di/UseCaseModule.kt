package id.my.mufidz.mandirinews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.my.mufidz.mandirinews.data.local.SearchDao
import id.my.mufidz.mandirinews.repository.NewsRepository
import id.my.mufidz.mandirinews.usecase.DeleteHistoryUseCase
import id.my.mufidz.mandirinews.usecase.NewsUseCase
import id.my.mufidz.mandirinews.usecase.SearchHistoryUseCase
import id.my.mufidz.mandirinews.usecase.TopHeadlineUseCase
import id.my.mufidz.mandirinews.usecase.UpsertHistoryUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideTopHeadlineUseCase(newsRepository: NewsRepository): TopHeadlineUseCase =
        TopHeadlineUseCase(newsRepository)

    @Singleton
    @Provides
    fun provideNewsUseCase(newsRepository: NewsRepository): NewsUseCase =
        NewsUseCase(newsRepository)

    @Singleton
    @Provides
    fun provideSearchHistory(searchDao: SearchDao): SearchHistoryUseCase =
        SearchHistoryUseCase(searchDao)

    @Singleton
    @Provides
    fun provideUpsertHistory(searchDao: SearchDao): UpsertHistoryUseCase =
        UpsertHistoryUseCase(searchDao)

    @Singleton
    @Provides
    fun provideDeleteHistory(searchDao: SearchDao): DeleteHistoryUseCase =
        DeleteHistoryUseCase(searchDao)

}