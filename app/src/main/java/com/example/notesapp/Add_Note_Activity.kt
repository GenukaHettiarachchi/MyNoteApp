package com.example.notesapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.databinding.ActivityAddnoteBinding
import android.view.Window
import android.view.WindowManager

class Add_Note_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityAddnoteBinding // Declare a binding object
    private lateinit var db: Notes_Database_Helper // Declare a database helper object (interact with the local database)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddnoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Notes_Database_Helper(this)   // Initialize the database helper.

        // Set up the On-click-listener for the "Save Button".
        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEdittext.text.toString()
            val note = Note(0, title, content)
            db.insertNote(note)
            finish()
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()  // Show a toast message
        }

        // Change the status bar color
        changeStatusBarColor("#FFAF45")
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