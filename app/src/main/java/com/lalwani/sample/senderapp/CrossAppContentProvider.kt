package com.lalwani.sample.senderapp

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.util.Log
import java.sql.SQLException


class CrossAppContentProvider : ContentProvider() {
    private var database: SQLiteDatabase? = null
    override fun onCreate(): Boolean {
        val dbHelper = CrossAppDatabaseHelper(context)
        database = dbHelper.getWritableDatabase()
        return database != null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val cursor = database!!.query("data_table", projection, selection, selectionArgs, null, null, sortOrder)
        cursor.setNotificationUri(context!!.contentResolver, uri)
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val rowId = database!!.insert("data_table", null, values)
        if (rowId > 0) {
            val newUri = ContentUris.withAppendedId(CONTENT_URI, rowId)
            context!!.contentResolver.notifyChange(newUri, null)
            Log.d("yyyy", "Inserted row into $newUri")
            return newUri
        }
        Log.d("yyyy", "Failed to insert row into $uri")
        throw SQLException("Failed to insert row into $uri")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    companion object {
        private const val AUTHORITY = "com.lalwani.sample.crossappprovider"
        val CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/data")
    }
}

