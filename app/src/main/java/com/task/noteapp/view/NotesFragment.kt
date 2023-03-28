package com.task.noteapp.view

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.task.noteapp.R
import com.task.noteapp.adapter.NoteRecyclerAdapter
import com.task.noteapp.databinding.FragmentNotesBinding
import com.task.noteapp.roomdb.Note
import com.task.noteapp.viewmodel.NotesViewModel
import javax.inject.Inject

class NotesFragment @Inject constructor(val noteRecyclerAdapter: NoteRecyclerAdapter, val glide: RequestManager) : Fragment(R.layout.fragment_notes) {

    private var fragmentNotesBinding: FragmentNotesBinding? = null
    lateinit var viewModel: NotesViewModel

    var notesList = arrayListOf<Note>();

    lateinit var txtInfo:TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(NotesViewModel::class.java)

        val binding = FragmentNotesBinding.bind(view)
        fragmentNotesBinding = binding

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.notes_title)

        subscribeToObservers()

        val recyclerViewNotes = binding.recyclerViewNotes
        txtInfo = binding.txtInfo
        val fab = binding.fab

        noteRecyclerAdapter.notes = notesList
        recyclerViewNotes.adapter = noteRecyclerAdapter
        recyclerViewNotes.layoutManager = LinearLayoutManager(requireContext())

        fab.setOnClickListener { view ->
            val action = NotesFragmentDirections.actionNotesFragmentToEditFragment()
            action.setNew(true)
            action.setNote(null)
            findNavController().navigate(action)
        }

        noteRecyclerAdapter.itemClickListener = { itemId, position ->
            when(itemId) {
                R.id.edit -> {
                    val selectedNote = noteRecyclerAdapter.notes[position]
                    val action = NotesFragmentDirections.actionNotesFragmentToEditFragment()
                    action.setNew(false)
                    action.setNote(selectedNote)
                    findNavController().navigate(action)
                }

                R.id.delete -> {
                    val selectedNote = noteRecyclerAdapter.notes[position]
                    viewModel.deleteNote(selectedNote)
                }
            }
        }
    }

    private fun subscribeToObservers() {
        viewModel.noteList.observe(viewLifecycleOwner, Observer { notes ->
            notesList.clear();
            notesList.addAll(notes)
            noteRecyclerAdapter.notifyDataSetChanged()

            if(notes.size == 0) {
                txtInfo.visibility = View.VISIBLE
            }
            else {
                txtInfo.visibility = View.GONE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentNotesBinding = null
    }
}