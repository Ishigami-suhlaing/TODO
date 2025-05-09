      package com.example.todo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.databinding.ActivityMainBinding

      class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
          private lateinit var db: DatabaseHelper
          lateinit var adapter: ToDoAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
db = DatabaseHelper(this)
        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()


        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnAddTodo.setOnClickListener {
              startActivity(Intent(this, AddTODO::class.java))
        }



         adapter = ToDoAdapter(this, db.getAllData())
        binding.rvTodo.layoutManager = LinearLayoutManager(this)
        binding.rvTodo.adapter = adapter


    }

          override fun onResume() {
              super.onResume()
                adapter.refresh(db.getAllData())
          }
}