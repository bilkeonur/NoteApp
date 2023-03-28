package com.task.noteapp.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.task.noteapp.adapter.NoteRecyclerAdapter
import javax.inject.Inject

class NoteFragmentFactory @Inject constructor(
    private val noteRecyclerAdapter: NoteRecyclerAdapter,
    private val glide: RequestManager): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            NotesFragment::class.java.name -> NotesFragment(noteRecyclerAdapter,glide)
            else -> super.instantiate(classLoader, className)
        }
    }
}