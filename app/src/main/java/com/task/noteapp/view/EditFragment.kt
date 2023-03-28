package com.task.noteapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.task.noteapp.R
import com.task.noteapp.databinding.FragmentEditBinding
import com.task.noteapp.roomdb.Note
import com.task.noteapp.util.Status
import com.task.noteapp.viewmodel.EditViewModel
import javax.inject.Inject

class EditFragment @Inject constructor() : Fragment(R.layout.fragment_edit) {

    private var fragmentEditBinding: FragmentEditBinding? = null
    lateinit var viewModel: EditViewModel

    var isNew: Boolean = false
    var currentNote: Note? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(EditViewModel::class.java)

        val binding = FragmentEditBinding.bind(view)
        fragmentEditBinding = binding

        val edtTitle = binding.edtTitle
        val edtDescription = binding.edtDescription
        val edtURL = binding.edtURL
        val btnSave = binding.btnSave

        arguments?.let {
            isNew = EditFragmentArgs.fromBundle(it).new
            currentNote = EditFragmentArgs.fromBundle(it).note

            if(!isNew) {
                (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.edit_note_title)
                edtTitle.setText(currentNote?.title)
                edtDescription.setText(currentNote?.description)
                edtURL.setText(currentNote?.imageURL)
            }
            else {
                (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.new_note_title)
            }
        }

        btnSave.setOnClickListener {

            val strTitle = edtTitle.text.toString()
            val strDescription = edtDescription.text.toString()
            val strURL = edtURL.text.toString()

            if(isNew) {
                viewModel.makeNewNote(strTitle, strDescription, strURL)
            }
            else {
                val newNote = Note(currentNote?.id,strTitle,strDescription,strURL,currentNote?.creationDate,true)
                viewModel.updateOldNote(newNote)
            }
        }

        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.insertNoteMessage.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    viewModel.resetInsertNoteMsg()
                    findNavController().popBackStack()
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentEditBinding = null
    }
}