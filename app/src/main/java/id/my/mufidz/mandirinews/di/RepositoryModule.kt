package id.my.mufidz.mandirinews.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.my.mufidz.mandirinews.repository.NewsRepository
import id.my.mufidz.mandirinews.repository.NewsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideNewsRepository(repositoryImpl: NewsRepositoryImpl) : NewsRepository
}