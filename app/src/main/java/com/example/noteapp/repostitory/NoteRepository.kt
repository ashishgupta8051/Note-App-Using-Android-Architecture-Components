package com.example.noteapp.repostitory

import androidx.lifecycle.LiveData
import com.example.noteapp.database.NoteDao
import com.example.noteapp.model.Note

class NoteRepository (private val noteDao:NoteDao){

    suspend fun insertNote(note:Note){
        noteDao.insertNote(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }

    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

    val allNotes : LiveData<List<Note>> = noteDao.getAllNote()
}