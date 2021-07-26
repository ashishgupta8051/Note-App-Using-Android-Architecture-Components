package com.example.noteapp.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.model.Note
import com.example.noteapp.viewModel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NoteAdapter(private val context: Context,
                  private val listener: ClickListener,
                  private val noteViewModel:NoteViewModel)
    : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){

    private val allNotes = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val viewHolder = NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.notelist,parent,false))
        viewHolder.deleteNote.setOnClickListener{
            listener.onDeleteItem(allNotes[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val notes = allNotes[position]
        holder.noteTxt.text = notes.noteText
        holder.date.text = notes.noteDate
        holder.id.text = notes.noteId.toString()+"."

        holder.itemView.setOnClickListener {
             var date: Calendar = Calendar.getInstance()
            var dateFormat = SimpleDateFormat("dd-MMM-yyyy")
            var currentDate =  dateFormat.format(date.time)

            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val view = LayoutInflater.from(context).inflate(R.layout.updatenotes,null)

            val editText:EditText = view.findViewById(R.id.update_editTxt)
            val updateBtn: Button = view.findViewById(R.id.updateBtn)
            val cancelBtn: Button = view.findViewById(R.id.cancelBtn)
            editText.setText(notes.noteText)

            builder.setView(view)
            val alertDialog = builder.create()
            alertDialog.show()

            updateBtn.setOnClickListener{
                if (editText.text.toString().isEmpty()){
                    editText.requestFocus()
                    editText.error = "Editbox should not be empty."
                }else{
                    noteViewModel.updateNote(Note(notes.noteId,editText.text.toString(),currentDate))
                    Toast.makeText(context,"Update Successful",Toast.LENGTH_SHORT).show()
                    alertDialog.dismiss()
                    alertDialog.dismiss()
                }
            }

            cancelBtn.setOnClickListener{
                alertDialog.dismiss()
            }

        }
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun addAllList(list: List<Note>){
        allNotes.clear()
        allNotes.addAll(list)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date:TextView = itemView.findViewById(R.id.date)
        val noteTxt:TextView = itemView.findViewById(R.id.noteTxt)
        val deleteNote:ImageView = itemView.findViewById(R.id.deleteNotes)
        val id:TextView = itemView.findViewById(R.id.noteId)
    }
}

interface ClickListener{
    fun onDeleteItem(note: Note)
}