package com.example.mc_finalproject

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

class MyDatabase {
    object MyDBContract {
        object MyEntry: BaseColumns {
            const val TABLE_NAME = "myDBfile"
            const val image_id = "image_id"
            const val image = "image"
            const val date = "date"
            const val place = "place"
            const val name = "name"
            const val comment = "comment"
        }
    }

    class MyDbHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${MyDBContract.MyEntry.TABLE_NAME} (" +
                    "${MyDBContract.MyEntry.image_id} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${MyDBContract.MyEntry.image} BLOB," +
                    "${MyDBContract.MyEntry.date} TEXT," +
                    "${MyDBContract.MyEntry.place} TEXT," +
                    "${MyDBContract.MyEntry.name} TEXT," +
                    "${MyDBContract.MyEntry.comment} TEXT)"
        val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${MyDBContract.MyEntry.TABLE_NAME}"

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(SQL_CREATE_ENTRIES)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL(SQL_DELETE_ENTRIES)
            onCreate(db)
        }

        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }
        companion object {
            const val DATABASE_VERSION = 1
            const val DATABASE_NAME = "myDBfile.db"
        }

        // 날짜 검색
        // "select * from" + "MyDBContract.MyEntry.TABLE_NAME" + "where date = date(입력값)"
        // 이름 검색
        // "select * from" + "MyDBContract.MyEntry.TABLE_NAME" + "where name = name(입력값)"

        fun selectAll(): MutableList<MyElement> {
            val readList = mutableListOf<MyElement>()
            val db = readableDatabase
            val cursor = db.rawQuery("SELECT * FROM " + MyDBContract.MyEntry.TABLE_NAME + ";", null)
            Log.d("TAG", "Select All Query: " + "SELECT * FROM " + MyDBContract.MyEntry.TABLE_NAME + ";")
            Log.d("TAG", cursor.toString())
            with(cursor){
                while (moveToNext()){
                    readList.add(MyElement(
                        cursor.getInt(0),
                        cursor.getBlob(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5))
                    )
                }
            }
            cursor.close()
            db.close()
            return readList
        }
    }
}
