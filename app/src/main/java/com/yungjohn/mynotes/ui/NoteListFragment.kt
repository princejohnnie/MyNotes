package com.yungjohn.mynotes.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yungjohn.mynotes.NoteClickListener
import com.yungjohn.mynotes.NoteListAdapter
import com.yungjohn.mynotes.R
import com.yungjohn.mynotes.database.NoteDatabaseDao
import com.yungjohn.mynotes.databinding.FragmentNotesBinding

import com.yungjohn.mynotes.notelist.NoteListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple fragment class used to display the notes offered by the [NoteListViewModel] on the UI
 */
const val NEW_NOTE_ID: Long = -1L
@AndroidEntryPoint
class NoteListFragment : Fragment() {

    private val viewModel by viewModels<NoteListViewModel>()

    private lateinit var binding: FragmentNotesBinding
    private lateinit var adapter: NoteListAdapter

    val notelistNavController: NavController? by lazy { view?.findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using DataBinding
        binding = FragmentNotesBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner

        binding.recyclerNotes.layoutManager = LinearLayoutManager(activity)
        binding.recyclerNotes.setHasFixedSize(true)


        adapter = NoteListAdapter(NoteClickListener { noteId ->
//            Log.d("NoteListFragment", "Note clicked with Id $noteId")
            viewModel.onNoteClicked(noteId)
        })

        binding.recyclerNotes.adapter = adapter

        binding.profileImage.setOnClickListener {
            this.findNavController().navigate(NoteListFragmentDirections.actionNotesFragmentToProfileFragment())
        }


        viewModel.notes.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                adapter.submit(it)
            } else
                binding.emptyListText.visibility = View.VISIBLE
        })

        viewModel.navigateToEditNote.observe(viewLifecycleOwner, { noteId ->
            noteId?.let {
                this.findNavController().navigate(NoteListFragmentDirections.actionNotesFragmentToEditNoteFragment())
                viewModel.onNavigatedToEditNote()
            }
        })

        /*binding.fab.setOnClickListener {
            val directions = NoteListFragmentDirections.actionNotesFragmentToSettingsFragment()
            it.findNavController().navigate(directions)
            viewModel.onNavigatedToEditNote()
        }*/
    }

    private fun deleteAllNotes() {
        viewModel.deleteAllNotes()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.action_delete_all)
        viewModel.notes.observe(viewLifecycleOwner, {
            if (it.isEmpty())
                item.isEnabled = false
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_delete_all -> deleteAllNotes()
            /*adapter.submitList(it)*/
        }
        return true
    }

    override fun onResume() {
        viewModel.notes.observe(viewLifecycleOwner, {
            if (it.isNotEmpty())
                binding.emptyListText.visibility = View.GONE
        })

        super.onResume()
    }
}