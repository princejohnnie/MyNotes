package com.yungjohn.mynotes.notelist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yungjohn.mynotes.database.NoteDatabaseDao
import com.yungjohn.mynotes.editnote.EditNoteViewModel
import javax.inject.Inject


class NoteListViewModelFactory (private val dataSource: NoteDatabaseDao): ViewModelProvider.NewInstanceFactory() {

    @Suppress("unchecked_cast")
    class Factory @Inject constructor(private val database: NoteDatabaseDao) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NoteListViewModel(database) as T
        }
    }
}
