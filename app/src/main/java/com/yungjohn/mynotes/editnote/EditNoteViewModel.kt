package com.yungjohn.mynotes.editnote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yungjohn.mynotes.R
import com.yungjohn.mynotes.database.Note
import com.yungjohn.mynotes.database.NoteDatabaseDao
import kotlinx.coroutines.*

/**
Created by John on 06/07/2021
 **/
class EditNoteViewModel(noteId : Long, dataSource: NoteDatabaseDao): ViewModel() {
    val database = dataSource

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val currentNote = MediatorLiveData<Note>()

    fun getNote() = currentNote

    private val _isNewNote = MutableLiveData<Boolean>()
    val isNewNote: LiveData<Boolean> get() = _isNewNote


    init {
        if(noteId != -1L){
            currentNote.addSource(database.getNote(noteId), currentNote::setValue)
            _isNewNote.value = false
        }else{
           _isNewNote.value = true
           // Log.d("EditNoteViewModel:", "Create new note here")
        }
    }

    /*fun getCurrentNote(noteId: Long): Note{
        uiScope.launch {
            withContext(Dispatchers.IO){
                val note = database.getNote(noteId)
            }
        }
    }*/

     fun createNote(note: Note) {
        uiScope.launch {
           insert(note)
        }
    }

    private suspend fun insert(note: Note){
        withContext(Dispatchers.IO){
            database.insert(note)
        }
    }

    fun updateNote(note: Note) {
        uiScope.launch {
            update(note)
        }
    }

    private suspend fun update(note: Note){
        withContext(Dispatchers.IO){
            database.update(note)
        }
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}