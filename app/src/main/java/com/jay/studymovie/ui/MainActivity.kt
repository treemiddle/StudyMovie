package com.jay.studymovie.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.jay.studymovie.R

class MainActivity : AppCompatActivity() {
    private lateinit var etQuery: EditText
    private lateinit var btnSearch: Button
    private lateinit var rv: RecyclerView
    private lateinit var progressbar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun initView() {
        etQuery = findViewById(R.id.et_query)
        btnSearch = findViewById(R.id.btn_search)
        rv = findViewById(R.id.rv_search_result)
        progressbar = findViewById(R.id.pb_loading)
    }
}