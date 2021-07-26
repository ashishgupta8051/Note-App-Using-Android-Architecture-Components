package com.example.noteapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)val noteId:Int,
    @ColumnInfo(name = "noteText")val noteText:String,
    @ColumnInfo(name = "noteDate")val noteDate:String
)