package com.example.quicknotes.ui.notes

import android.R.layout
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ArrayAdapter
import com.example.quicknotes.databinding.NotesFragmentBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.quicknotes.models.Record
import kotlin.reflect.typeOf

fun readJson(): List<Record> {
    val jsonList =
        """[{"title": "Kotlin Tutorial", "author": "bezkoder", "text" : "sometext"},
            {"title": "Kotlin Tutorial", "author": "bezkoder", "text" : "sometext"},
            {"title": "Kotlin Tutorial", "author": "bezkoder", "text" : "sometext"}]""".trimMargin()

    val gson = Gson()

    val listRecordType = object : TypeToken<List<Record>>() {}.type
    var records: List<Record> = gson.fromJson(jsonList, listRecordType)

    return records
}

class NotesFragment : Fragment() {

    private lateinit var notesViewModel: NotesViewModel
    private var _binding: NotesFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NotesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NotesFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listView: ListView = binding.recordList

        val recordArray = readJson()

        val listItems = arrayOfNulls<String>(recordArray.size)

        for (i in 0 until recordArray.size) {
            val record = recordArray[i]
            listItems[i] = record.title
        }

        val adapter = ArrayAdapter(
            activity!!,
            android.R.layout.simple_list_item_1,
            listItems
        )
        listView.adapter = adapter

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}