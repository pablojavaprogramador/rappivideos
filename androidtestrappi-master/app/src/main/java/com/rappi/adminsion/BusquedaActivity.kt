package com.rappi.adminsion

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class BusquedaActivity  : AppCompatActivity() {
    private val TAG = "TAG Busquedad  "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)
        val intent: Intent = getIntent()
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            Log.d(TAG, "Query Ejecutada: $query")
        }
    }

}