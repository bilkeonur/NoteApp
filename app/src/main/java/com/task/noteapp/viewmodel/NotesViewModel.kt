package com.task.noteapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.noteapp.repo.INoteRepository
import com.task.noteapp.roomdb.Note
import com.task.noteapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val repository: INoteRepository) : ViewModel() {

    var noteList = repository.getNotes()

    private var insertNoteMsg = MutableLiveData<Resource<Note>>()

    val insertNoteMessage: LiveData<Resource<Note>>
        get() = insertNoteMsg

    fun resetInsertNoteMsg() {
        insertNoteMsg = MutableLiveData<Resource<Note>>()
    }
    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }
}