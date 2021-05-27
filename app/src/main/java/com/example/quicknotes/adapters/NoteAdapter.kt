package com.example.quicknotes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.quicknotes.R
import com.example.quicknotes.models.Record


class NoteAdapter(
    context: Context,
    private val dataSource: List<Record>
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    //1
    override fun getCount(): Int {
        return dataSource.size
    }

    //2
    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    //3
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //4
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.note_item, parent, false)

        // Get title element
        val noteNameView = rowView.findViewById(R.id.note_name) as TextView

        // Get subtitle element
        val noteAuthorView = rowView.findViewById(R.id.note_author) as TextView

        // Get detail element
        val noteTextView = rowView.findViewById(R.id.note_text) as TextView

        val note = getItem(position) as Record

        noteNameView.text = note.title
        noteAuthorView.text = note.author
        noteTextView.text = note.text

        return rowView
    }
}