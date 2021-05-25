package com.example.quicknotes.ui.notes

import android.R.layout
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat.recreate
import com.example.quicknotes.MainActivity
import com.example.quicknotes.adapters.NoteAdapter
import com.example.quicknotes.databinding.NotesFragmentBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.quicknotes.models.Record
import com.example.quicknotes.ui.NoteViewActivity
import java.io.File
import java.io.InputStream

fun checkFile(context: Context) {
    var fileObject = File(context.getFilesDir().getPath().toString() + "notes.json")
    var fileExists = fileObject.exists()

    if (!fileExists) {
        fileObject.createNewFile()
//        val value = mutableListOf<Record>()
        val jsonString = """[{"id": 1, "title": "Kotlin Tutorial1", "author": "bezkoder", "text" : "sometext"},
            {"id": 2, "title": "Kotlin Tutorial2", "author": "bezkoder", "text" : "sometext"},
            {"id": 3, "title": "Kotlin Tutorial3", "author": "bezkoder", "text" : "sometext"}]"""

        val gson = Gson()

        val listRecordType = object : TypeToken<MutableList<Record>>() {}.type
        var value: MutableList<Record> = gson.fromJson(jsonString, listRecordType)

        writeJson(value, context)
    }
}

fun readJson(context: Context): MutableList<Record> {
    checkFile(context)

    val inputStream: InputStream = File(context.getFilesDir().getPath().toString() + "notes.json").inputStream()
    val jsonString = inputStream.bufferedReader().use { it.readText() }

    val gson = Gson()

    val listRecordType = object : TypeToken<MutableList<Record>>() {}.type
    var records: MutableList<Record> = gson.fromJson(jsonString, listRecordType)

    return records
}

fun writeJson(l: MutableList<Record>, context: Context){
    val myFile = File(context.getFilesDir().getPath().toString() + "notes.json")
    val gson = Gson()

    val s = gson.toJson(l)

    myFile.writeText(s)
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

        val recordArray = readJson(activity!!)

        val adapter = NoteAdapter(activity!!, recordArray)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedNote = recordArray[position]

            val detailIntent = NoteViewActivity.newIntent(activity!!, selectedNote)

            startActivity(detailIntent)
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}