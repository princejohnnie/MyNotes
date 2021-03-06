package com.yungjohn.mynotes.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yungjohn.mynotes.R
import com.yungjohn.mynotes.database.Note
import com.yungjohn.mynotes.database.NoteDatabaseDao
import com.yungjohn.mynotes.databinding.FragmentEditNoteBinding
import com.yungjohn.mynotes.editnote.EditNoteViewModel
import com.yungjohn.mynotes.editnote.EditNoteViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple fragment class used to edit and save notes
 */
@AndroidEntryPoint
class EditNoteFragment : Fragment() {
    @Inject lateinit var dataSource: NoteDatabaseDao

    private val viewModel: EditNoteViewModel by viewModels()
    private lateinit var binding: FragmentEditNoteBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditNoteBinding.inflate(inflater, container, false)

        //val application = requireNotNull(this.activity).application
       // val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao



        binding.editNoteViewModel = viewModel
        binding.lifecycleOwner = this

        (activity)?.title = "Note"

        binding.editNoteTitle.setOnClickListener {
            // Log.d("EditNoteFragment: ", "onClick called")
            binding.editNoteTitle.isCursorVisible = true
        }

        binding.editNoteText.setOnClickListener {
           // Log.d("EditNoteFragment: ", "onClick called")
            binding.editNoteText.isCursorVisible = true
        }


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save_note){
            if (binding.editNoteText.text.isEmpty() || binding.editNoteTitle.text.isEmpty()){
                Toast.makeText(activity, "Cannot create empty note", Toast.LENGTH_SHORT).show()
                navigateBack()
                return true
            }
            viewModel.isNewNote.observe(viewLifecycleOwner, { isNewNote ->
                if (isNewNote){
                    saveNewNote()
                    navigateBack()
                }else{
                    updateNote()
                    navigateBack()
                }
            })
            // Hide the keyboard.
            hideKeyboard()

           // navigateBack()
            return true

        }else if (item.itemId == R.id.action_delete_note){
            val note = viewModel.note.value
            if (note != null) {
                deleteNote(note)
                navigateBack()
            }
           // return true
        }
        return true
    }

    private fun navigateBack(){

    }

    private fun deleteNote(note: Note) {
        viewModel.deleteNote(note)
    }

    private fun hideKeyboard() {
        val imm = (activity as AppCompatActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        binding.editNoteText.isCursorVisible = false
        binding.editNoteTitle.isCursorVisible = false
    }

    private fun updateNote() {
        val noteTitle = binding.editNoteTitle.text.toString()
        val noteText = binding.editNoteText.text.toString()
//        val note = Note(noteTitle, noteText, arguments.noteId)

//        viewModel.updateNote(note)
        Toast.makeText(activity, "Note Updated", Toast.LENGTH_SHORT).show()
    }

    private fun saveNewNote() {
        val noteTitle = binding.editNoteTitle.text.toString()
        val noteText = binding.editNoteText.text.toString()

        val note = Note(noteTitle, noteText)

        viewModel.createNote(note)
        Toast.makeText(activity, "Note Saved", Toast.LENGTH_SHORT).show()

    }
}
