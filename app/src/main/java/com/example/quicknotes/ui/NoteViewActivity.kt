package com.example.quicknotes.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.quicknotes.MainActivity
import com.example.quicknotes.R
import com.example.quicknotes.models.Record

class NoteViewActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_URL = "text"

        fun newIntent(context: Context, recipe: Record): Intent {
            val detailIntent = Intent(context, NoteViewActivity::class.java)

            detailIntent.putExtra(EXTRA_TITLE, recipe.title)
            detailIntent.putExtra(EXTRA_URL, recipe.text)

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

        val title = intent.extras?.getString(EXTRA_TITLE)
        val url = intent.extras?.getString(EXTRA_URL)

        setTitle(title)
    }
}