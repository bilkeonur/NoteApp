package com.task.noteapp.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.task.noteapp.launchFragmentInHiltContainer
import com.task.noteapp.repo.FakeNoteRepository
import com.task.noteapp.viewmodel.EditViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject
import com.task.noteapp.R
import com.task.noteapp.getOrAwaitValueTest
import com.task.noteapp.roomdb.Note
import com.task.noteapp.viewmodel.NotesViewModel

@MediumTest
@HiltAndroidTest
class EditFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: NoteFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testOnBackPressed() {

        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<EditFragment>(factory = fragmentFactory) {
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.pressBack()

        Mockito.verify(navController).popBackStack()
    }

    @Test
    fun testSave() {
        val testEditViewModel = EditViewModel(FakeNoteRepository())
        val testNotesViewModel = NotesViewModel(FakeNoteRepository())

        launchFragmentInHiltContainer<EditFragment>(factory = fragmentFactory) {
            viewModel = testEditViewModel
        }

        Espresso.onView(withId(R.id.edtTitle)).perform(replaceText("Test Title"))
        Espresso.onView(withId(R.id.edtDescription)).perform(replaceText("Test Description"))
        Espresso.onView(withId(R.id.edtURL)).perform(replaceText("Test Image"))
        Espresso.onView(withId(R.id.btnSave)).perform(click())

        assertThat(testNotesViewModel.noteList.getOrAwaitValueTest()).contains(
            Note(1, "Test Title","Test Description", "Test Image",  "27/03/2023", false)
        )
    }
}