package com.yungjohn.mynotes.notelist

import android.app.Application
import androidx.lifecycle.*
import com.yungjohn.mynotes.database.Note
import com.yungjohn.mynotes.database.NoteDatabaseDao
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
Created by John on 06/07/2021
 **/

class NoteListViewModel @Inject constructor(val database: NoteDatabaseDao) : ViewModel(){
    private val _navigateToEditNote = MutableLiveData<Long>()

    private var _notes = database.getAllNotes()

    val notes: LiveData<List<Note>>
        get() = _notes

    init {

    }

    val navigateToEditNote: LiveData<Long>
        get() = _navigateToEditNote

    fun onNoteClicked(id: Long){
        _navigateToEditNote.value = id
    }

    fun onNavigatedToEditNote(){
        _navigateToEditNote.value = null
    }

    fun deleteAllNotes(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                database.deleteAllNotes()
            }
        }
    }
}
