package com.zjq.jetpackcompose2023.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

class MyContentProvider: ContentProvider() {
    override fun onCreate(): Boolean {

    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {

    }

    override fun getType(p0: Uri): String? {

    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {

    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {

    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {

    }
}