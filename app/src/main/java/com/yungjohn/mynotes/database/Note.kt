package com.yungjohn.mynotes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @ColumnInfo(name = "note_title")
    val noteTitle: String,

    @ColumnInfo(name = "note_text")
    val noteText: String,

    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0
)
