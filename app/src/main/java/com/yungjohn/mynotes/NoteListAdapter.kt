package com.yungjohn.mynotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yungjohn.mynotes.database.Note
import com.yungjohn.mynotes.databinding.NoteItemBinding

/**
Created by John on 06/07/2021
 **/
class NoteListAdapter(private val clickListener: NoteClickListener): ListAdapter<Note, NoteListAdapter.ViewHolder>(NoteListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = NoteItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    class ViewHolder(private val binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: NoteClickListener, item: Note){
            binding.note = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

}

class NoteListDiffCallback : DiffUtil.ItemCallback<Note>(){
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.noteId == newItem.noteId
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}

class NoteClickListener(val clickListener: (noteId: Long) -> Unit){
    fun onClick(note: Note) = clickListener(note.noteId)
}