package com.task.noteapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.noteapp.R
import com.task.noteapp.repo.INoteRepository
import com.task.noteapp.roomdb.Note
import com.task.noteapp.util.Helper
import com.task.noteapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel@Inject constructor(private val repository: INoteRepository) : ViewModel() {

    private var insertNoteMsg = MutableLiveData<Resource<Note>>()

    val insertNoteMessage: LiveData<Resource<Note>>
        get() = insertNoteMsg

    fun resetInsertNoteMsg() {
        insertNoteMsg = MutableLiveData<Resource<Note>>()
    }

    fun insertNote(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun makeNewNote(title: String, description: String, imageURL: String, isNew:Boolean = true) {
        if(title.isEmpty()) {
            insertNoteMsg.postValue(Resource.error("Please Enter Title", null))
            return
        }

        if(description.isEmpty()) {
            insertNoteMsg.postValue(Resource.error("Please Enter Description", null))
            return
        }

        val creationDate = Helper.getDate()

        val note = Note(title = title, description = description, imageURL = imageURL, creationDate = creationDate)
        insertNote(note)
        insertNoteMsg.postValue(Resource.success(note))
    }

    fun updateOldNote(note:Note) {

        if(note.title!!.isEmpty()) {
            insertNoteMsg.postValue(Resource.error("Please Enter Title", null))
            return
        }

        if(note.description!!.isEmpty()) {
            insertNoteMsg.postValue(Resource.error("Please Enter Description", null))
            return
        }

        updateNote(note)
        insertNoteMsg.postValue(Resource.success(note))
    }
}