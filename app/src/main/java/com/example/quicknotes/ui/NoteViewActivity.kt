package com.example.quicknotes.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.quicknotes.MainActivity
import com.example.quicknotes.R
import com.example.quicknotes.models.Record
import com.example.quicknotes.ui.notes.readJson
import com.example.quicknotes.ui.notes.writeJson

class NoteViewActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = -1
        const val EXTRA_TITLE = "title"
        const val EXTRA_TEXT = "text"

        fun newIntent(context: Context, recipe: Record): Intent {
            val detailIntent = Intent(context, NoteViewActivity::class.java)

            detailIntent.putExtra("EXTRA_TITLE", recipe.title)
            detailIntent.putExtra("EXTRA_TEXT", recipe.text)
            detailIntent.putExtra("EXTRA_ID", recipe.note_id.toInt())

            return detailIntent
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_view)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);

        val title = intent.extras?.getString("EXTRA_TITLE")
        val text = intent.extras?.getString("EXTRA_TEXT")
        val id = intent.extras?.getInt("EXTRA_ID")

        val titlefield: TextView = findViewById(R.id.titlefield)
        titlefield.text = title
        val textbody: TextView = findViewById(R.id.textbody)
        textbody.text = text

        val deleteButton: Button = findViewById(R.id.delete_button)

        deleteButton.setOnClickListener {
            val currList = readJson(applicationContext)
            currList.removeAll{ it.note_id == id }
            writeJson(currList, context = applicationContext)
            onBackPressed()
        }

        setTitle(title)
    }
}