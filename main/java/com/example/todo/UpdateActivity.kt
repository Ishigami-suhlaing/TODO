package com.example.todo

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todo.databinding.ActivityUpdateBinding
import java.text.SimpleDateFormat
import java.util.Locale

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private val db = DatabaseHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var id = intent.getIntExtra("id", -1)
        var todoDataById = db.getNoteById(id)
        binding.etTitle.setText(todoDataById.title)
        binding.etDes.setText(todoDataById.description)
        binding.txtDate.text = todoDataById.date
        binding.txtTime.text = todoDataById.time
        
        binding.btnBack.setOnClickListener { 
            onBackPressed()
        }
        
        binding.fbUpdate.setOnClickListener {
            var title: String= binding.etTitle.text.toString()
            var description: String  = binding.etDes.text.toString()
            var date: String = todoDataById.date
            var time: String = todoDataById.time


            var todo = ToDo(id, title, description, date, time)

            db.updateData(todo)
            finish()
            Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show()


        }

    }
}