package com.notsatria.noteapp.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.notsatria.noteapp.R
import com.notsatria.noteapp.adapter.NotesAdapter
import com.notsatria.noteapp.databinding.FragmentNoteListBinding

class NoteListFragment : Fragment() {

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddNote.setOnClickListener { view ->
            Log.d(TAG, "onCreateView: fabAddNote clicked")
            view.findNavController().navigate(R.id.action_noteListFragment_to_addNoteFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showRecyclerList(adapter: NotesAdapter) {
        binding.rvNotes.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvNotes.adapter = adapter
    }


    private fun showDeleteDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder
            .setTitle("Delete Note")
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                showToast("Note deleted")
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = builder.create()

        dialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        val TAG = NoteListFragment::class.java.name
        val EXTRA_NOTE = "extra_note"
    }
}