package com.task.noteapp.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.noteapp.roomdb.Note

class FakeNoteRepository: INoteRepository {

    private val notes = mutableListOf<Note>()
    private val notesLiveData = MutableLiveData<List<Note>>(notes)

    override suspend fun insertNote(note: Note) {
        notes.add(note)
        refreshData()
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
        refreshData()
    }

    override suspend fun updateNote(note: Note) {
    }

    override fun getNotes(): LiveData<List<Note>> {
        return notesLiveData
    }

    private fun refreshData() {
        notesLiveData.postValue(notes)
    }
}