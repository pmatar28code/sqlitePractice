package com.mine.sqlite_practice

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {
    companion object{
        val DATABASE_NAME = "notesapp.db"
        val DATABASE_VERSION = 1
        val TABLE_NAME = "allNotes"
        val COLUMN_ID = "id"
        val COLUMN_CONTENT = "content"
        val COLUMN_TITLE = "title"
    }
    override fun onCreate(p0: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_TITLE TEXT,$COLUMN_CONTENT TEXT)"
        p0?.execSQL(createTableQuery)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        p0?.execSQL(dropTableQuery)
        onCreate(p0)
    }

    fun inserNote(note:Note?){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_TITLE,note?.title)
            put(COLUMN_CONTENT,note?.content)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun getAllNotes():List<Note>{
        val notesList = mutableListOf<Note>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString( cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content =cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            notesList.add(Note(id,title,content))
        }
        cursor.close()
        db.close()
        return notesList
    }

    fun getCurrentIndex():Int{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)
        var idForAdd = 0
        while(cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            idForAdd = id
        }
        cursor.close()
        db.close()
        return idForAdd
    }

    fun updateNote(note: Note){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,note.title)
            put(COLUMN_CONTENT,note.content)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(note.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }

    fun getNoteById(noteId:Int):Note{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Note(id,title,content)
    }

    fun deleteNote(noteId:Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs= arrayOf(noteId.toString())

        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()

    }

}

