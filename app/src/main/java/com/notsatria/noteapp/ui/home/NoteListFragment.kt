package com.notsatria.noteapp.ui.home

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        binding.rvNotes.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvNotes.adapter = adapter
    }

    private fun obtainViewModel(application: Application): HomeViewModel {
        val factory = ViewModelFactory.getInstance(application)
        return ViewModelProvider(requireActivity(), factory).get(HomeViewModel::class.java)
    }
}