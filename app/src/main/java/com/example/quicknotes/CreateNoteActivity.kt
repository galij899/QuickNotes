package com.example.quicknotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.quicknotes.models.Record
import com.example.quicknotes.ui.notes.readJson
import com.example.quicknotes.ui.notes.writeJson

class CreateNoteActivity : AppCompatActivity() {

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);

        val submitButton = findViewById<Button>(R.id.create)
        val titleField = findViewById<EditText>(R.id.title)
        val textField = findViewById<EditText>(R.id.text)
        val errors = findViewById<TextView>(R.id.errors)

        errors.text = ""

        submitButton.setOnClickListener {
            if (titleField.text.toString().isNullOrBlank()) {
                errors.text = "Введите заголовок"
                if (textField.text.toString().isNullOrBlank()) {
                    errors.text = "Введите заголовок и заметку"
                }
            } else {
                if (textField.text.toString().isNullOrBlank()) {
                    errors.text = "Введите текст заметки"
                } else {
                    val recordsList = readJson(applicationContext)
                    val allIds = recordsList.map { it.note_id }
                    val maxOrNull = allIds.maxOrNull()
                    val maxId = if (maxOrNull != null) maxOrNull + 1 else 1

                    val newRecord = Record(
                        note_id = maxId,
                        title = titleField.text.toString(),
                        text = textField.text.toString(),
                        author = "author1"
                    )

                    recordsList.add(newRecord)
                    writeJson(recordsList, applicationContext)
                    onBackPressed()
                }
            }

        }

    }
}