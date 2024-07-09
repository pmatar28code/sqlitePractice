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

           // Log.e("new Note = ","$newNote")

            saveBtn.setOnClickListener {
                val title = titleEt.text.toString()
                val content = contentEt.text.toString()
                val newNote = Note(0, title, content)
                db.inserNote(newNote)
//                finish()

                Log.e("new Note = ","$newNote")

            }
        }
    }
}