package com.example.todo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import androidx.core.content.contentValuesOf

class DatabaseHelper( context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_DES TEXT, $COLUMN_DATE TEXT, $COLUMN_TIME TEXT)"
        db?.execSQL(createTableQuery)    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)

    }

    fun insertData(todo: ToDo){
        val db = writableDatabase
        val values = contentValuesOf().apply {
            put(COLUMN_TITLE, todo.title)
            put(COLUMN_DES,todo.description)
            put(COLUMN_DATE, todo.date)
            put(COLUMN_TIME, todo.time)
        }

        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    
    fun getAllData():List<ToDo>{
        val todoList = mutableListOf<ToDo>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while(cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DES))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
            val time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME))

            val todo = ToDo(id, title, description, date, time)
            todoList.add(todo)
        }
        cursor.close()
        db.close()
        return todoList
    }
    
fun deleteData(noteID:Int){
    val db = writableDatabase
    val whereClause = "$COLUMN_ID = ?"
    val whereArgs = arrayOf(noteID.toString())
    db.delete(TABLE_NAME, whereClause, whereArgs)
    db.close()

}

    fun getNoteById(noteID: Int): ToDo{
        val db = readableDatabase

        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteID"
        val cursor = db.rawQuery(query, null)

        // It's important to move the cursor to the first row.
        // If the noteID might not exist, a check like 'if (cursor.moveToFirst())' would be more robust.
        // Given the non-nullable return type, this implementation assumes the noteID is valid and a row will be found.

        var todo = ToDo(noteID, "Error", "error", "$noteID", "404")
        if(cursor.moveToFirst()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DES))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
            val time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME))

             todo = ToDo(id, title, description, date, time)
        }

        cursor.close()
        db.close() // Consistent with other database operations in this class.

        return todo

    }

    fun  updateData(todo: ToDo){
        val db = writableDatabase
        val values = contentValuesOf().apply {
            put(COLUMN_TITLE, todo.title)
            put(COLUMN_DES,todo.description)
            put(COLUMN_DATE, todo.date)
            put(COLUMN_TIME, todo.time)
        }

        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf((todo.id).toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    companion object{
        private const val DATABASE_NAME ="student.db"
        private const val DATABASE_VERSION = 1
            private const val TABLE_NAME = "todo"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DES = "description"
        private const val COLUMN_DATE = "date"
            private const val COLUMN_TIME = "time"
    }
}