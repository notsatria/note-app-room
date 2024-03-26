package com.notsatria.noteapp.ui.home

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.notsatria.noteapp.R
import com.notsatria.noteapp.adapter.NotesAdapter
import com.notsatria.noteapp.data.local.entity.NoteEntity
import com.notsatria.noteapp.databinding.FragmentNoteListBinding

class NoteListFragment : Fragment() {

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)

        val viewModel = obtainViewModel(requireActivity().application)

        viewModel.getAllNotes().observe(viewLifecycleOwner) { notes ->
            Log.d("NoteListFragment", "onCreateView: $notes")
            if (notes != null) {
                val adapter = NotesAdapter(notes)
                showRecyclerList(adapter)

                if (adapter.itemCount == 0) {
                    binding.rvNotes.visibility = View.GONE
                    binding.emptyView.visibility = View.VISIBLE
                } else {
                    binding.rvNotes.visibility = View.VISIBLE
                    binding.emptyView.visibility = View.GONE
                }
            }
        }

        binding.fabAddNote.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_noteListFragment_to_addNoteFragment)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showRecyclerList(adapter: NotesAdapter) {
        binding.rvNotes.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvNotes.adapter = adapter

        adapter.setOnItemClickCallback(object : NotesAdapter.OnItemClickCallback {
            override fun onItemClicked(view: View, note: NoteEntity) {
                Log.d("NoteListFragment", "onItemClicked: $note")
                val mBundle = Bundle()
                mBundle.putParcelable(EXTRA_NOTE, note)
                view.findNavController().navigate(
                        R.id.action_noteListFragment_to_addNoteFragment,
                        mBundle
                    )
            }

            override fun onItemLongClicked(note: NoteEntity) {
                Log.d("NoteListFragment", "onItemLongClicked: $note")
                showDeleteDialog(requireContext(), note)
            }
        }
        )
    }
    private fun obtainViewModel(application: Application): HomeViewModel {
        val factory = ViewModelFactory.getInstance(application)
        return ViewModelProvider(requireActivity(), factory).get(HomeViewModel::class.java)
    }

    private fun showDeleteDialog(context: Context, note: NoteEntity) {
        val builder = AlertDialog.Builder(context)
        builder
            .setTitle("Delete Note")
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Yes") { dialog, _ ->
                val viewModel = obtainViewModel(requireActivity().application)
                viewModel.delete(note)
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
        val EXTRA_NOTE = "extra_note"
    }
}