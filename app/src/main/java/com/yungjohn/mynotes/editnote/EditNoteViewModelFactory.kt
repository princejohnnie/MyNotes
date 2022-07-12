package com.yungjohn.mynotes.editnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yungjohn.mynotes.database.NoteDatabaseDao
import javax.inject.Inject

/**
Created by John on 09/07/2021
 **/
class EditNoteViewModelFactory(private val noteId: Long, private val dataSource: NoteDatabaseDao): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditNoteViewModel(noteId, dataSource) as T
    }
}
