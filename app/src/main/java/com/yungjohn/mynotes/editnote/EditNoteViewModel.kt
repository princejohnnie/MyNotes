package com.yungjohn.mynotes.editnote

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
class EditNoteViewModel(val noteId : Long, dataSource: NoteDatabaseDao): ViewModel() {
    val database = dataSource

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _currentNote = MediatorLiveData<Note>()
    fun getNote() = _currentNote

    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note>
        get() = _note

    private val _isNewNote = MutableLiveData<Boolean>()
    val isNewNote: LiveData<Boolean> get() = _isNewNote

    init {
        initializeNote()
        if(noteId != -1L){
            _currentNote.addSource(database.getNote(noteId), _currentNote::setValue)
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

    fun initializeNote(){
        uiScope.launch {
            _note.value = getNoteWithId(noteId)
        }
    }

    private suspend fun getNoteWithId(id: Long): Note {
        return withContext(Dispatchers.IO){
            val note = database.getNoteWithId(id)
            note
        }
    }

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

    fun deleteNote(note: Note) {
        uiScope.launch {
            delete(note)
        }
    }

    private suspend fun delete(note: Note){
        withContext(Dispatchers.IO){
            database.delete(note)
        }
    }


    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}