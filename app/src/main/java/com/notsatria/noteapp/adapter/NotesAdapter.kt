package com.notsatria.noteapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.notsatria.noteapp.data.local.entity.NoteEntity
import com.notsatria.noteapp.databinding.NoteItemBinding
import com.notsatria.noteapp.utils.NoteDiffCallback

class NotesAdapter(val listNotes: List<NoteEntity>) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NoteEntity) {
            with(binding) {
                tvTitle.text = note.title
                tvContent.text = note.note
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listNotes.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }
}