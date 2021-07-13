package com.yungjohn.mynotes.notelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yungjohn.mynotes.NoteClickListener
import com.yungjohn.mynotes.NoteListAdapter
import com.yungjohn.mynotes.R
import com.yungjohn.mynotes.database.NoteDatabase
import com.yungjohn.mynotes.databinding.FragmentNotesBinding

/**
 * A simple [Fragment] subclass.
 * Use the [NoteListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class NoteListFragment : Fragment() {
    private val NEW_NOTE_ID: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using DataBinding
        val binding: FragmentNotesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao
        val viewModelFactory = NoteListViewModelFactory(dataSource, application)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(NoteListViewModel::class.java)
        binding.lifecycleOwner = this

        binding.notesViewModel = viewModel
        binding.recyclerNotes.layoutManager = LinearLayoutManager(activity)

        val adapter = NoteListAdapter(NoteClickListener { noteId ->
            Toast.makeText(context, "Note clicked with Id $noteId", Toast.LENGTH_SHORT).show()
            viewModel.onNoteClicked(noteId)
        })

        binding.recyclerNotes.adapter = adapter

        viewModel.navigateToEditNote.observe(viewLifecycleOwner, { noteId ->
            noteId?.let {
                this.findNavController().navigate(NoteListFragmentDirections.actionNotesFragmentToEditNoteFragment(noteId))
                viewModel.onNavigatedToEditNote()
            }
        })

        viewModel.notes.observe(viewLifecycleOwner, {
            if(it.isNotEmpty()){
                adapter.submitList(it)
            }
        })

        binding.fab.setOnClickListener {
            it.findNavController().navigate(NoteListFragmentDirections.actionNotesFragmentToEditNoteFragment(NEW_NOTE_ID))
        }

        return binding.root
    }

}