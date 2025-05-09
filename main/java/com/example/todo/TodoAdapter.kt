package com.example.todo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class ToDoAdapter(context:Context,var todoList: List<ToDo>): RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {
private  var  db: DatabaseHelper = DatabaseHelper(context)
    class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title = itemView.findViewById<TextView>(R.id.title_card)
        var date = itemView.findViewById<TextView>(R.id.date_card)
        var edit = itemView.findViewById<ImageView>(R.id.edit_card)
        var delete = itemView.findViewById<ImageView>(R.id.delete_card )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ToDoAdapter.ToDoViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.todo_card, parent, false)
        return ToDoViewHolder(view)

    }

    override fun onBindViewHolder(holder: ToDoAdapter.ToDoViewHolder, position: Int) {
        var todo = todoList[position]
        holder.title.text = todo.title
        holder.date.text = todo.date
        holder.delete.setOnClickListener {
            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete Task")
            builder.setMessage("Are you sure you want to delete this task: ${todo.title}?")
            builder.setCancelable(false)


            builder.setPositiveButton("Yes") { dialog, _ ->
                db.deleteData(todo.id)
                refresh(db.getAllData())
                Toast.makeText(context, "Delete!!", Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
        holder.edit.setOnClickListener { 
            val context = holder.itemView.context
            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("id", todo.id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun refresh(newNote:List<ToDo>){
        todoList = newNote
        notifyDataSetChanged()

    }

}