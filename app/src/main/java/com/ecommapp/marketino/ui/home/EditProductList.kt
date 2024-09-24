package com.ecommapp.marketino.ui.home

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ecommapp.marketino.R

class EditProductList : AppCompatActivity() {
    private lateinit var saveBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_product_list)

        saveBtn = findViewById(R.id.but_save)
        saveBtn.setOnClickListener {

        }

    }
}