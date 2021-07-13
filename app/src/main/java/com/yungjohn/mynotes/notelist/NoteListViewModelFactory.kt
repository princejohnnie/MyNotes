package com.yungjohn.mynotes.notelist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yungjohn.mynotes.database.NoteDatabaseDao
import com.yungjohn.mynotes.editnote.EditNoteViewModel

class NoteListViewModelFactory(private val dataSource: NoteDatabaseDao, val application: Application): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteListViewModel::class.java)) {
            return NoteListViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}