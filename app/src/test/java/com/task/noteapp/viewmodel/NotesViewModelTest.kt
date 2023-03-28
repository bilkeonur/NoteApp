package com.task.noteapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.task.noteapp.MainCoroutineRule
import com.task.noteapp.getOrAwaitValueTest
import com.task.noteapp.repo.FakeNoteRepository
import com.task.noteapp.util.Status
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NotesViewModelTest {
    private lateinit var viewModel: NotesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        viewModel = NotesViewModel(FakeNoteRepository())
    }
}