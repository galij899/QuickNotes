package com.example.quicknotes.models

class Record(
    val id: Int,
    val title: String,
    val author: String,
    val text: String
) {
    override fun toString(): String {
        return "Record ${this.id}"
    }
}