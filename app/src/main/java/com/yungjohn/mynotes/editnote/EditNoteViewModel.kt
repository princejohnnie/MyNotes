package com.yungjohn.mynotes.editnote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    val currentNote = MediatorLiveData<Note>()

    fun getNote() = currentNote

    private val _newNote = MutableLiveData<Note>()
    val newNote: LiveData<Note> get() = _newNote


    init {
        if(noteId != -1L){
            currentNote.addSource(database.getNote(noteId), currentNote::setValue)
        }else{
           // createNewNote(note)
           // Log.d("EditNoteViewModel:", "Create new note here")
        }
    }

     fun createNewNote(note: Note) {
        uiScope.launch {
           insert(note)
        }
    }

    private suspend fun insert(note: Note){
        withContext(Dispatchers.IO){
            database.insert(note)
        }
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}