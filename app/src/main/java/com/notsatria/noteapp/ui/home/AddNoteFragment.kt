package com.notsatria.noteapp.ui.home

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.notsatria.noteapp.R
import com.notsatria.noteapp.data.local.entity.NoteEntity
import com.notsatria.noteapp.databinding.FragmentAddNoteBinding
import com.notsatria.noteapp.helpers.DateHelper

class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = obtainViewModel(requireActivity().application)

        val etTitle = binding.etTitle
        val etNote = binding.etNote

        val dataNote = arguments?.getParcelable<NoteEntity>(NoteListFragment.EXTRA_NOTE)

        if (dataNote != null) {
            etTitle.setText(dataNote.title)
            etNote.setText(dataNote.note)
        }

        binding.fabSaveNote.setOnClickListener {
            if (etTitle.text.toString().isNotEmpty() || etNote.text.toString().isNotEmpty()) {
                val title = etTitle.text.toString()
                val note = etNote.text.toString()
                val noteEntity = NoteEntity(null, title = title, note = note, date = DateHelper.getCurrentDate())
                if (dataNote != null) {
                    noteEntity.id = dataNote.id
                    viewModel.update(noteEntity)
                    showToast(getString(R.string.success_update_note_message))
                } else {
                    viewModel.insert(noteEntity)
                    showToast(getString(R.string.success_add_note_message))
                }
                it.findNavController().popBackStack()
            } else {
                showToast(getString(R.string.empty_field_message))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(application: Application): HomeViewModel {
        val factory = ViewModelFactory.getInstance(application)
        return ViewModelProvider(requireActivity(), factory).get(HomeViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddNoteFragment()
    }
}