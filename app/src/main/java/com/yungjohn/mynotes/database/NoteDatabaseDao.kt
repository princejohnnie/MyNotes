package com.yungjohn.mynotes.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDatabaseDao {
    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("delete from note_table")
    fun deleteAllNotes()

    @Query("select * from note_table where noteId = :id")
    fun getNote(id: Long): LiveData<Note>

    @Query("select * from note_table where noteId = :id")
    fun getNoteWithId(id: Long): Note

    @Query("select * from note_table ORDER BY noteId")
    fun getAllNotes(): LiveData<List<Note>>

}