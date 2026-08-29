package com.lalessio.patientkiosk.di

import android.content.Context
import com.lalessio.patientkiosk.data.json.QuestionnaireImporter
import com.lalessio.patientkiosk.data.local.AppDatabase
import com.lalessio.patientkiosk.data.local.dao.QuestionnaireDao
import com.lalessio.patientkiosk.data.local.dao.SessionDao
import com.lalessio.patientkiosk.data.repo.QuestionnaireRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.build(context)

    @Provides
    fun provideQuestionnaireDao(database: AppDatabase): QuestionnaireDao =
        database.questionnaireDao()

    @Provides
    fun provideSessionDao(database: AppDatabase): SessionDao =
        database.sessionDao()

    @Provides
    @Singleton
    fun provideImporter(
        @ApplicationContext context: Context,
        database: AppDatabase
    ): QuestionnaireImporter = QuestionnaireImporter(context, database)

    @Provides
    @Singleton
    fun provideQuestionnaireRepository(
        dao: QuestionnaireDao,
        importer: QuestionnaireImporter
    ): QuestionnaireRepository = QuestionnaireRepository(dao, importer)
}