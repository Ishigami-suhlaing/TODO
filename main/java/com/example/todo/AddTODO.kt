package com.example.todo

import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todo.databinding.ActivityAddTodoBinding
import com.example.todo.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Locale

class AddTODO : AppCompatActivity() {
    lateinit var binding: ActivityAddTodoBinding
    private lateinit var databaseHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        databaseHelper = DatabaseHelper(this)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        var formattedDate: String = dateFormat.format(currentTime).toString()
        var formattedTime: String = timeFormat.format(currentTime).toString()
        binding.txtDate.text = formattedDate
        binding.txtTime.text = formattedTime

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.fbSave.setOnClickListener {

            var title: String= binding.etTitle.text.toString()
            var description: String  = binding.etDes.text.toString()
            var todo = ToDo(0,title, description, formattedDate, formattedTime)
            databaseHelper.insertData(todo)
            Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show()
finish()
        }
    }
}