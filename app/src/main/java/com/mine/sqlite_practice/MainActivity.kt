package com.mine.sqlite_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.mine.sqlite_practice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var db : DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(context = this)

        binding.apply {
            saveBtn.setOnClickListener {
                val title = titleEt.text.toString()
                val content = contentEt.text.toString()
                val currentId = if(db.getCurrentIndex() == 0){
                  db.getCurrentIndex()
                }else {
                    db.getCurrentIndex() + 1
                }
                Log.e("current id + 1 = ","$currentId")
                val newNote = Note(currentId, title, content)
                db.inserNote(newNote)

                Log.e("new Note = ","$newNote")
            }
            getBtn.setOnClickListener{
                val notesList =  db.getAllNotes()
                Log.e("all notes list","$notesList")
            }
        }
    }
}