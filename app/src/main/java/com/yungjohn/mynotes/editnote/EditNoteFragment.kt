package com.yungjohn.mynotes.editnote

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Database
import com.yungjohn.mynotes.R
import com.yungjohn.mynotes.database.Note
import com.yungjohn.mynotes.database.NoteDatabase
import com.yungjohn.mynotes.databinding.FragmentEditNoteBinding

/**
 * A simple [Fragment] subclass.
 * Use the [EditNoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditNoteFragment : Fragment() {
    private lateinit var application: Application
    private lateinit var viewModel: EditNoteViewModel
    private lateinit var binding: FragmentEditNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_note, container, false)

        application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao
        val arguments = EditNoteFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = EditNoteViewModelFactory(arguments.noteId, dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EditNoteViewModel::class.java)

        binding.editNoteViewModel = viewModel
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save_note){
            if (binding.editNoteText.text.isNotEmpty() && binding.editNoteTitle.text.isNotEmpty()){
                saveNewNote()
                Toast.makeText(activity, "Note Saved", Toast.LENGTH_SHORT).show()
                // Hide the keyboard.
                val imm = (activity as AppCompatActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
            }
        }
        return true
    }

    private fun saveNewNote() {
        val noteTitle = binding.editNoteTitle.text.toString()
        val noteText = binding.editNoteText.text.toString()
        val note = Note(noteTitle, noteText)

        viewModel.createNewNote(note)
    }
}