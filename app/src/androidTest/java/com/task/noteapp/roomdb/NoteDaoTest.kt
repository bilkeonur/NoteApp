package com.task.noteapp.roomdb

import androidx.test.filters.SmallTest
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.task.noteapp.getOrAwaitValueTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class NoteDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var dao: NoteDao

    @Inject
    @Named("testDatabase")
    lateinit var database: NoteDatabase

    @Before
    fun setup() {
        /*database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NoteDatabase::class.java).allowMainThreadQueries().build()*/

        hiltRule.inject()
        dao = database.noteDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertNoteTesting() = runBlocking {
        val note = Note(
            id = 1,
            title = "Test Title",
            description = "Test Description",
            imageURL = "https://png.pngtree.com/element_our/20190603/ourmid/pngtree-sticky-note-cartoon-illustration-image_1430615.jpg",
            creationDate = "26/03/2023")

        dao.insertNote(note)
        val list = dao.observeNotes().getOrAwaitValueTest()
        assertThat(list).contains(note)
    }

    @Test
    fun deleteNoteTesting() = runBlocking {
        val note = Note(
            id = 1,
            title = "Test Title",
            description = "Test Description",
            imageURL = "https://png.pngtree.com/element_our/20190603/ourmid/pngtree-sticky-note-cartoon-illustration-image_1430615.jpg",
            creationDate = "26/03/2023")

        dao.insertNote(note)
        dao.deleteNote(note)
        val list = dao.observeNotes().getOrAwaitValueTest()
        assertThat(list).doesNotContain(note)
    }

    @Test
    fun updateNoteTesting() = runBlocking {
        val note1 = Note(
            id = 1,
            title = "Test Title 1",
            description = "Test Description 1",
            imageURL = "https://png.pngtree.com/element_our/20190603/ourmid/pngtree-sticky-note-cartoon-illustration-image_1430615.jpg",
            creationDate = "26/03/2023")

        dao.insertNote(note1)

        val note2 = Note(
            id = 1,
            title = "Test Title 2",
            description = "Test Description 2",
            imageURL = "",
            creationDate = "26/03/2023")

        dao.updateNote(note2)

        val list = dao.observeNotes().getOrAwaitValueTest()
        assertThat(list).contains(note2)
    }
}