package com.task.noteapp.repo

import androidx.lifecycle.LiveData
import com.task.noteapp.roomdb.Note
import com.task.noteapp.roomdb.NoteDao
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) : INoteRepository {
    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    override fun getNotes(): LiveData<List<Note>> {
        return noteDao.observeNotes()
    }
}