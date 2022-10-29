package com.example.testyourself.data.di

import com.example.testyourself.data.repository.ExamApiRepositoryImpl
import com.example.testyourself.domain.repositories.ExamApiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ExamApiRepositoryModule {

    @Binds
    @Singleton
    fun getFirestoreRepository(examApiRepositoryImpl: ExamApiRepositoryImpl): ExamApiRepository
}