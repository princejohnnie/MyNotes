package com.yungjohn.mynotes.util

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.yungjohn.mynotes.database.Note

@BindingAdapter("setVisible")
fun TextView.setVisible(notes: List<Note>?){
    notes?.let {
        if (it.isNotEmpty())
            visibility = View.INVISIBLE
    }
}

@BindingAdapter("setDate")
fun TextView.setDate(timestamp: Long){
    text = formatDate(timestamp)
}