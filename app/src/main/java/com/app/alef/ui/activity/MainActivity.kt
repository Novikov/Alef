package com.app.alef.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.app.alef.R

class MainActivity : AppCompatActivity(), Activity {
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
    }

    override fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar?.visibility = View.INVISIBLE
    }

    override fun hideActionBar() {
        supportActionBar?.hide()
    }

    override fun showActionBar() {
        supportActionBar?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        progressBar = null
    }
}