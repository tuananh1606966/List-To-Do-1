package com.nghiemtuananh.sqlitedatabasekpt

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int,
) : SQLiteOpenHelper(context, name, factory, version) {

    // truy vấn không trả kết quả: CREATE, INSERT, UPDATE, DELETE...

    fun queryData(sql: String) {
        var database: SQLiteDatabase = writableDatabase
        database.execSQL(sql)
    }

    // truy vấn có trả kết quả: SELECT

    fun getData(sql: String) : Cursor {
        var database: SQLiteDatabase = readableDatabase
        return database.rawQuery(sql, null)
    }

    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}