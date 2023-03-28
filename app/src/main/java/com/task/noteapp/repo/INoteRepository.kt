package com.task.noteapp.repo

import androidx.lifecycle.LiveData
import com.task.noteapp.roomdb.Note

interface INoteRepository {
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note:Note)
    suspend fun updateNote(note: Note)
    fun getNotes(): LiveData<List<Note>>
}