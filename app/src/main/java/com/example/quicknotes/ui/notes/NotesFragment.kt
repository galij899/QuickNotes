package com.example.quicknotes.ui.notes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.quicknotes.R
import com.example.quicknotes.databinding.FragmentHomeBinding
import com.example.quicknotes.databinding.NotesFragmentBinding
import com.example.quicknotes.ui.home.HomeViewModel

class NotesFragment : Fragment() {

    private lateinit var notesViewModel: NotesViewModel
    private var _binding: NotesFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NotesViewModel

    fun read_json(): {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {_binding = NotesFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.sampleText
        textView.text = "lololo"
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}