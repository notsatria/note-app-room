package com.notsatria.noteapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.notsatria.noteapp.data.local.entity.NoteEntity
import com.notsatria.noteapp.data.local.room.NoteDao
import com.notsatria.noteapp.data.local.room.NoteRoomDatabase
import com.notsatria.noteapp.utils.AppExecutors

class NoteRepository(application: Application) {
    private val mNoteDao: NoteDao
    private val appExecutor: AppExecutors

    init {
        val db = NoteRoomDatabase.getDatabase(application)
        mNoteDao = db.noteDao()
        appExecutor = AppExecutors()
    }

    fun getAllNotes() : LiveData<List<NoteEntity>> = mNoteDao.getAllNotes()

    fun insert(note: NoteEntity) {
        appExecutor.diskIO.execute { mNoteDao.insert(note) }
    }

    fun update(note: NoteEntity) {
        appExecutor.diskIO.execute { mNoteDao.update(note) }
    }

    fun delete(note: NoteEntity) {
        appExecutor.diskIO.execute { mNoteDao.delete(note) }
    }
}