package com.task.noteapp.viewmodel

import com.task.noteapp.repo.FakeNoteRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.task.noteapp.MainCoroutineRule
import com.task.noteapp.getOrAwaitValueTest
import com.task.noteapp.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class EditViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: EditViewModel

    @Before
    fun setup() {
        viewModel = EditViewModel(FakeNoteRepository())
    }

    @Test
    fun `insert note without title returns error`() {
        viewModel.makeNewNote("","Test Description", "")
        val value = viewModel.insertNoteMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert note without description returns error`() {
        viewModel.makeNewNote("Test Title","", "")
        val value = viewModel.insertNoteMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}