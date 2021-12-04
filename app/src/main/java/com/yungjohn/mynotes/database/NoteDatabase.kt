package com.yungjohn.mynotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
Created by John on 09/07/2021
 **/

@Database(entities = [Note::class], version = 3, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
    abstract val noteDatabaseDao : NoteDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(context.applicationContext,
                    NoteDatabase::class.java, "note_database")
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}