package com.example.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//Manage the SQLite database for storing notes.
class Notes_Database_Helper(context:Context ) :SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION){

    // Define constants for database configuration.
    companion object{
        private const val DATABASE_NAME="MyNotes.db"
        private const val DATABASE_VERSION=1
        private const val TABLE_NAME="MyNotesAll"
        private const val COLUMN_ID="Note_ID"
        private const val COLUMN_TITLE="Title"
        private const val COLUMN_CONTENT="Note"
    }

    // Called when the database is created for the first time.
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_TITLE TEXT,$COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    // Called when needs to be upgrade the database.
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    // Insert a new note into the database.
    fun insertNote(note: Note) {
        val db =writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Retrieve all notes from the database.
    fun getAllNotes():List<Note> {
        val notesList = mutableListOf<Note>()
        val db = readableDatabase
        val query = "SELECT*FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while(cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

           val note=Note(id,title,content)
            notesList.add(note)

       }

        cursor.close()
        db.close()
        return notesList

    }

    // Update an existing note in the database.
    fun updateNote(note: Note){
        val db =writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)

        }
        val whereClause = "$COLUMN_ID =?"
        val whereArgs = arrayOf(note.id.toString())
        db.update(TABLE_NAME, values, whereClause,whereArgs)
        db.close()
    }

    // Retrieve a note by its ID from the database.
    fun getNoteByID(noteId:Int):Note{
        val db=readableDatabase
        val query="SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        val cursor =db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))


        cursor.close()
        db.close()
        return Note(id,title,content)
    }

    // Delete a note from the database by its ID.
    fun deleteNote(noteId:Int){

        val db=writableDatabase
        val whereClause="$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteId.toString())
        db.delete(TABLE_NAME, whereClause,whereArgs)
        db.close()
    }





}