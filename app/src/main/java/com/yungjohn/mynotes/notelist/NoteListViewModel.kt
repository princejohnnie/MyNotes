package com.yungjohn.mynotes.notelist

import android.app.Application
import androidx.lifecycle.*
import com.yungjohn.mynotes.database.Note
import com.yungjohn.mynotes.database.NoteDatabaseDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * ViewModel class for the [NoteListFragment]
 * Contains relevant methods and properties used to manipulate controls in the [NoteListFragment]
 */
@HiltViewModel
class NoteListViewModel @Inject constructor(val database: NoteDatabaseDao) : ViewModel() {

    private var _notes = database.getAllNotes()

    val notes: LiveData<List<Note>>
        get() = _notes


    private val _navigateToEditNote = MutableLiveData<Long?>()
    val navigateToEditNote: MutableLiveData<Long?>
        get() = _navigateToEditNote

    init {
       // getAllNotes()
    }

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
