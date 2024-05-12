package com.example.notesapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.databinding.ActivityMainBinding
import android.view.Window
import android.view.WindowManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding  // Declare a binding object
    private lateinit var db: Notes_Database_Helper  // Declare a database helper object (interact with the local database)
    private lateinit var notesAdapter: Notes_Adapter // Declare an adapter for the RecyclerView to display the notes.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Notes_Database_Helper(this)  // Initialize the database helper.
        notesAdapter = Notes_Adapter(db.getAllNotes(), this)  // Initialize the adapter with the initial list of notes.

        // Set up the RecyclerView
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = notesAdapter

        // Set up the On-click-listener for the "Add Button"
        binding.addButton.setOnClickListener {
            val intent = Intent(this, Add_Note_Activity::class.java)
            startActivity(intent)
        }

        // Change the status bar color
        changeStatusBarColor("#FFAF45")

    }

    override fun onResume() {
        super.onResume()
        notesAdapter.refreshData(db.getAllNotes())  // Refresh the data
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