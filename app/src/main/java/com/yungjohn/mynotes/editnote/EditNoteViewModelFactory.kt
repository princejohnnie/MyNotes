package com.yungjohn.mynotes.editnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yungjohn.mynotes.database.NoteDatabaseDao

/**
Created by John on 09/07/2021
 **/
class EditNoteViewModelFactory(private val noteId: Long, private val dataSource: NoteDatabaseDao): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditNoteViewModel::class.java)){
            return EditNoteViewModel(noteId, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}