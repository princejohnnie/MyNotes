package com.yungjohn.mynotes.di

import android.content.Context
import com.yungjohn.mynotes.database.Note
import com.yungjohn.mynotes.database.NoteDatabase
import com.yungjohn.mynotes.database.NoteDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NoteDatabase{
        return NoteDatabase.getInstance(context)
    }

    @Provides
    fun provideNoteDao(database: NoteDatabase): NoteDatabaseDao{
        return database.noteDatabaseDao
    }

}