package com.yungjohn.mynotes.notelist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yungjohn.mynotes.database.Note
import com.yungjohn.mynotes.database.NoteDatabaseDao
import kotlinx.coroutines.*

/**
Created by John on 06/07/2021
 **/

class NoteListViewModel(val database: NoteDatabaseDao, application: Application) : AndroidViewModel(application){
    private val _navigateToEditNote = MutableLiveData<Long>()
   // private val _noteClicked

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _notes = database.getAllNotes()

    val notes: LiveData<List<Note>>
        get() = _notes

    init {
       // initializeNote()
    }

    private fun initializeNote() {
        uiScope.launch {
            val note = Note("Test Title", "Test String", 2L)
            insert(note)
        }
    }

    private suspend fun insert(note: Note){
        withContext(Dispatchers.IO){
            database.insert(note)
        }
    }

/*    private fun initializeNotes(){
         _notes.value = mutableListOf(
            Note(1L, "First Title", "First Text"),
            Note(2L, "Second Title", "Second Text"),
            Note(3L, "Third Title", "Third Text"),
             Note(4L, "Fourth Title", "Fourth Text"),
             Note(5L, "Fifth Title", "Fifth Text"),
             Note(6L, "Sixth Title", "Sixth Text"),
             Note(7L, "Seventh Title", "Seventh Text"),
             Note(8L, "Eighth Title", "Eighth Text"),
             Note(9L, "Ninth Title", "Ninth Text"),
             Note(10L, "Tenth Title", "Tenth Text")
             
        )
    }*/

    val navigateToEditNote: LiveData<Long>
        get() = _navigateToEditNote

    fun onNoteClicked(id: Long){
        _navigateToEditNote.value = id
    }

    fun onNavigatedToEditNote(){
        _navigateToEditNote.value = null
    }

    fun deleteAllNotes(){
        uiScope.launch {
            withContext(Dispatchers.IO){
                database.deleteAllNotes()
            }
        }
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}