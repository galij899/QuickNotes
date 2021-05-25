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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

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
                    val allIds = recordsList.map { it.id }
                    val maxId = allIds.maxOrNull() ?: 0 + 1

                    val newRecord = Record(
                        id = maxId,
                        title = titleField.text.toString(),
                        text = textField.text.toString(),
                        author = "author1"
                    )

                    recordsList.add(newRecord)
                    writeJson(recordsList, applicationContext)
                }
            }

        }

    }
}