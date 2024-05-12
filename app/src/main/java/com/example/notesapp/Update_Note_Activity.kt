package com.example.notesapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.example.notesapp.databinding.ActivityUpdateNoteBinding


class Update_Note_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding  // Declare a binding object
    private lateinit var db: Notes_Database_Helper  // Declare a database helper object
    private var noteId : Int = -1   // Variable to store the ID of the note being updated.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the database helper.
        db = Notes_Database_Helper(this)

        // Retrieve the note ID from the intent extras.
        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1){
            finish()
            return
        }

        // Retrieve the note details from the database.
        val note =  db.getNoteByID(noteId)
        binding.updatetitleEditText.setText(note.title)
        binding.updatecontentEdittext.setText(note.content)

        // Set up the click listener for the "Save Button".
        binding.updatesaveButton.setOnClickListener{
            val newTitle = binding.updatetitleEditText.text.toString()
            val newContent = binding.updatecontentEdittext.text.toString()

            // Create a new note object with the updated title and content.
            val updatedNote = Note(noteId, newTitle, newContent)

            // Update the note in the database.
            db.updateNote(updatedNote)
            finish()    // Finish the activity to return to the previous screen.
            Toast.makeText(this,"Changes Saved", Toast.LENGTH_SHORT).show()   // Show a toast message
        }
        // Change the status bar color
        changeStatusBarColor("#FFAF45") // Replace with your desired color code
    }

    // Change the status bar color.
    private fun changeStatusBarColor(color: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = android.graphics.Color.parseColor(color)
        }
    }
}