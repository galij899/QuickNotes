package com.example.quicknotes.models

class Record(
    val note_id: Int,
    val title: String,
    val author: String,
    val text: String
) {
    override fun toString(): String {
        return "Record ${this.note_id}"
    }
}