package com.example.quicknotes.models

class Record(
    val title: String,
    val author: String,
    val text: String
) {
    override fun toString(): String {
        return "Record [title: ${this.title}, author: ${this.author}, text: ${this.text}]"
    }
}