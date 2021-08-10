package com.yungjohn.mynotes.notelist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.yungjohn.mynotes.NoteClickListener
import com.yungjohn.mynotes.NoteListAdapter
import com.yungjohn.mynotes.R
import com.yungjohn.mynotes.database.NoteDatabase
import com.yungjohn.mynotes.database.NoteDatabaseDao
import com.yungjohn.mynotes.databinding.FragmentNotesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [NoteListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
const val NEW_NOTE_ID: Long = -1L
@AndroidEntryPoint
class NoteListFragment : Fragment() {
    @Inject lateinit var viewModel: NoteListViewModel
    @Inject lateinit var viewModelFactory: NoteListViewModelFactory
    @Inject lateinit var dataSource: NoteDatabaseDao

    private lateinit var binding: FragmentNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using DataBinding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false)

        //val application = requireNotNull(this.activity).application
       // val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao
       // val viewModelFactory = NoteListViewModelFactory(dataSource)
        //viewModel = ViewModelProvider(this, viewModelFactory).get(NoteListViewModel::class.java)

        binding.lifecycleOwner = this

        binding.notesViewModel = viewModel
        binding.recyclerNotes.layoutManager = LinearLayoutManager(activity)
        binding.recyclerNotes.setHasFixedSize(true)

        setHasOptionsMenu(true)
        (activity)?.title = "MyNotes"

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
            }else{
                binding.emptyText.visibility = View.VISIBLE
            }
        })

        binding.fab.setOnClickListener {
            it.findNavController().navigate(NoteListFragmentDirections.actionNotesFragmentToEditNoteFragment(NEW_NOTE_ID))
            viewModel.onNavigatedToEditNote()
        }

        return binding.root
    }

    private fun deleteAllNotes() {
        viewModel.deleteAllNotes()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.action_delete_all)
        if (viewModel.notes.value.isNullOrEmpty()){
            item.isEnabled = false
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_delete_all -> deleteAllNotes()

        }
        return true
    }
}