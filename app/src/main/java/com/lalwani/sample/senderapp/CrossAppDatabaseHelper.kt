package com.lalwani.sample.senderapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class CrossAppDatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        // Create table where the content provider will insert records
        db.execSQL(
            "CREATE TABLE data_table ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "package_name TEXT,"
                    + "install_time INTEGER);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades if necessary
    }

    companion object {
        private val DATABASE_NAME = "senderapp.db"
        private val DATABASE_VERSION = 1
    }
}

