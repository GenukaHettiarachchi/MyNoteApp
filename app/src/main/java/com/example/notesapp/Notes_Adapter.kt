package com.example.notesapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

//Represents the adapter for the RecyclerView
class Notes_Adapter (private var notes: List<Note>, context: Context) :
    RecyclerView.Adapter<Notes_Adapter.NoteViewHolder>() {

        // Declare a database helper object
        private val db: Notes_Database_Helper= Notes_Database_Helper(context)

    // ViewHolder class to hold the views for each item in the RecyclerView.
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextview)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton:ImageView =itemView.findViewById(R.id.updateButton)
        val deleteButton:ImageView =itemView.findViewById(R.id.deletButton)
    }

    // Create a new ViewHolder when the RecyclerView needs one.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent, false)
        return NoteViewHolder(view)

    }

    // Return the total number of items in the RecyclerView.
    override fun getItemCount(): Int =notes.size


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        // Set the title and content
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        // Set up the On-click-listener for the "Update Button"
        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, Update_Note_Activity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        // Set up the On-click-listener for the "Delete Button"
        holder.deleteButton.setOnClickListener{
            db.deleteNote(note.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context, "Note deleted ", Toast.LENGTH_SHORT).show()  // Show a toast message
        }

    }

    // Refresh the data
    fun refreshData(newNotes: List<Note>){
        notes = newNotes
        notifyDataSetChanged()
    }



}
