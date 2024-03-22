package com.notsatria.noteapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.notsatria.noteapp.data.local.entity.NoteEntity

class NoteDiffCallback(private val oldList: List<NoteEntity>, private val newList: List<NoteEntity>)
    : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldList[oldItemPosition]
        val newNote = newList[newItemPosition]
        return oldNote.title == newNote.title && oldNote.note == newNote.note
    }
}