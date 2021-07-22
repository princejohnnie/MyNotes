package com.yungjohn.mynotes.editnote

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.yungjohn.mynotes.R
import com.yungjohn.mynotes.database.Note
import com.yungjohn.mynotes.database.NoteDatabase
import com.yungjohn.mynotes.databinding.FragmentEditNoteBinding
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [EditNoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditNoteFragment : Fragment() {
    private lateinit var viewModel: EditNoteViewModel
    private lateinit var binding: FragmentEditNoteBinding
    private lateinit var arguments: EditNoteFragmentArgs
    private var focusChangedListener: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_note, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao
        arguments = EditNoteFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = EditNoteViewModelFactory(arguments.noteId, dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EditNoteViewModel::class.java)

        binding.editNoteViewModel = viewModel
        binding.lifecycleOwner = this


        binding.editNoteText.onFocusChangeListener = View.OnFocusChangeListener { editText, focusChanged ->
            focusChangedListener = focusChanged
           // Log.d("EditNoteFragment: ", "Focus changed $focusChanged")
        }
/*
        binding.editNoteText.onTouchEvent(MotionEvent.ACTION_DOWN as MotionEvent).apply {
            this.let {
                binding.editNoteText.isFocusable = true
            }
        }
*/
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.action_save_note)
        val itemDelete = menu.findItem(R.id.action_delete_note)
       // item.isVisible = binding.editNoteText.hasFocus()
        val hasFocus = binding.editNoteText.isFocused
       // Log.d("EditNoteFragment: ", "Does EditText have focus? $hasFocus")
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save_note){
            if (binding.editNoteText.text.isEmpty() || binding.editNoteTitle.text.isEmpty()){
                Toast.makeText(activity, "Cannot create empty note", Toast.LENGTH_SHORT).show()
                this.findNavController().navigate(EditNoteFragmentDirections.actionEditNoteFragmentToNotesFragment())
                return true
            }
            viewModel.isNewNote.observe(viewLifecycleOwner, { isNewNote ->
                if (isNewNote){
                    saveNewNote()
                }else{
                    updateNote()
                }
            })

            // Hide the keyboard.
            hideKeyboard()

        }else if (item.itemId == R.id.action_delete_note){
             viewModel.initializeNote()
            val note = viewModel.note.value
            if (note != null) {
                deleteNote(note)
            }
        }
        this.findNavController().navigate(EditNoteFragmentDirections.actionEditNoteFragmentToNotesFragment())
        return true
    }

    private fun deleteNote(note: Note) {
        viewModel.deleteNote(note)
    }

    private fun hideKeyboard() {
        val imm = (activity as AppCompatActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
      //  binding.editNoteText.isFocusable = false
        val hasFocus = binding.editNoteText.isFocused
       // Log.d("EditNoteFragment: ", "Does EditText have focus? $hasFocus")
    }

    private fun updateNote() {
        val noteTitle = binding.editNoteTitle.text.toString()
        val noteText = binding.editNoteText.text.toString()
        val note = Note(noteTitle, noteText, arguments.noteId)

        viewModel.updateNote(note)
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
