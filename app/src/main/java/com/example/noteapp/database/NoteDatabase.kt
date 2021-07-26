package com.example.noteapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteapp.model.Note

@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
public abstract class NoteDatabase : RoomDatabase(){

    abstract fun getNoteDao() :NoteDao

    companion object{
        private var INSTANCE : NoteDatabase? = null
        val DATABASE = "noteDb"

        fun getInstance(context: Context) : NoteDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    DATABASE
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}