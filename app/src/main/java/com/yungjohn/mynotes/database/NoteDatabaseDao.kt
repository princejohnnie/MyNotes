package com.yungjohn.mynotes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDatabaseDao {
    @Insert
    fun insert(note: Note)

    @Query("select * from note_table where noteId = :id")
    fun getNote(id: Long): LiveData<Note>

    @Query("select * from note_table ORDER BY noteId")
    fun getAllNotes(): LiveData<List<Note>>
}