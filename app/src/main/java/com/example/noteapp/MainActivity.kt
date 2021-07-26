package com.example.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.adapter.ClickListener
import com.example.noteapp.adapter.NoteAdapter
import com.example.noteapp.model.Note
import com.example.noteapp.viewModel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(),ClickListener {
    private lateinit var noteViewModel:NoteViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteViewModel = ViewModelProvider(this,ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application))
            .get(NoteViewModel::class.java)

        button = findViewById(R.id.submitBtn)
        editText = findViewById(R.id.editTxt)

        recyclerView = findViewById(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        noteAdapter = NoteAdapter(this, this,noteViewModel)
        recyclerView.adapter = noteAdapter


        noteViewModel.allNotes.observe(this, Observer
        { list ->
            list?.let{
                noteAdapter.addAllList(it)
            }
        })
    }

    override fun onDeleteItem(note: Note) {
        noteViewModel.deleteNote(note)
        Toast.makeText(this,"Delete Successful",Toast.LENGTH_SHORT).show()
    }

    fun onSubmit(view: View) {
        if (editText.text.toString().isEmpty()){
            editText.requestFocus()
            editText.error = "Enter text"
        }else{
            var date: Calendar = Calendar.getInstance()
            var dateFormat = SimpleDateFormat("dd-MMM-yyyy")
            var currentDate =  dateFormat.format(date.time)
            val updatedNoteText = editText.text.toString()

            noteViewModel.insertNote(Note(0,updatedNoteText, currentDate))
            Toast.makeText(this, "Item Inserted : ${editText.text.toString()}",Toast.LENGTH_SHORT).show()
            editText.text = null
        }
    }
}