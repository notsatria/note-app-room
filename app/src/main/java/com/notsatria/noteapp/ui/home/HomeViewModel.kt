package com.notsatria.noteapp.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import com.notsatria.noteapp.data.local.entity.NoteEntity
import com.notsatria.noteapp.data.repository.NoteRepository

class HomeViewModel(private val application: Application) : ViewModel() {
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    fun getAllNotes() = mNoteRepository.getAllNotes()

    fun insert(note: NoteEntity) = mNoteRepository.insert(note)

    fun update(note: NoteEntity) = mNoteRepository.update(note)

    fun delete(note: NoteEntity) = mNoteRepository.delete(note)
}