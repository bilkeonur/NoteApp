package com.task.noteapp.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.task.noteapp.R
import com.task.noteapp.repo.INoteRepository
import com.task.noteapp.repo.NoteRepository
import com.task.noteapp.roomdb.NoteDao
import com.task.noteapp.roomdb.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context) = Room.databaseBuilder(
            context, NoteDatabase::class.java, "NoteAppDb"
        ).build()

    @Singleton
    @Provides
    fun injectDao(database: NoteDatabase) = database.noteDao()

    @Singleton
    @Provides
    fun injectRepo(dao: NoteDao) = NoteRepository(dao) as INoteRepository

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.icn_note)
                .error(R.drawable.icn_note)
        )
}