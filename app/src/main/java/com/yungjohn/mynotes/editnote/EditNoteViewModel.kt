package com.yungjohn.mynotes.editnote

import androidx.lifecycle.*
import com.yungjohn.mynotes.database.Note
import com.yungjohn.mynotes.database.NoteDatabaseDao
import kotlinx.coroutines.*
import javax.inject.Inject

/**
Created by John on 06/07/2021
 **/
class EditNoteViewModel(val noteId : Long, val database: NoteDatabaseDao): ViewModel() {

    private val _currentNote = MediatorLiveData<Note>()

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

    fun initializeNote(){
        viewModelScope.launch {
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
        viewModelScope.launch {
           insert(note)
        }
    }

    private suspend fun insert(note: Note){
        withContext(Dispatchers.IO){
            database.insert(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            update(note)
        }
    }

    private suspend fun update(note: Note){
        withContext(Dispatchers.IO){
            database.update(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            delete(note)
        }
    }

    private suspend fun delete(note: Note){
        withContext(Dispatchers.IO){
            database.delete(note)
        }
    }
}