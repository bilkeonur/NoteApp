package com.task.noteapp.view

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.task.noteapp.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject
import com.task.noteapp.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class NotesFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: NoteFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testNavigationFromNotesToEdit() {

        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<NotesFragment>(factory = fragmentFactory) {
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.fab)).perform(ViewActions.click())

        Mockito.verify(navController).navigate(
            NotesFragmentDirections.actionNotesFragmentToEditFragment().setNew(true).setNote(null)
        )
    }
}