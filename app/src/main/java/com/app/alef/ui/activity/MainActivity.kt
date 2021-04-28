package com.app.alef.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.app.alef.R

class MainActivity : AppCompatActivity(), Activity {
    private var progressBar: ProgressBar? = null
    private var errorMessageTextView: TextView? = null
    private var isErrorMessageShoved = false
    private var retryButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
        errorMessageTextView = findViewById(R.id.msg_text_view)
        retryButton = findViewById(R.id.retry_button)
        retryButton!!.setOnClickListener {
            retry()
        }
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

    override fun showErrorMessage(msg: String) {
        errorMessageTextView?.text = msg
        errorMessageTextView?.visibility = View.VISIBLE
        retryButton?.visibility = View.VISIBLE
        isErrorMessageShoved = true
    }

    override fun clearErrorMessage() {
        errorMessageTextView?.text = ""
        errorMessageTextView?.visibility = View.INVISIBLE
        retryButton?.visibility = View.INVISIBLE
        isErrorMessageShoved = false
    }

    override fun isErrorMessageShoved(): Boolean {
        return isErrorMessageShoved
    }

    override fun retry() {
        if (isErrorMessageShoved) {
            clearErrorMessage()
        }

        val navController = findNavController(R.id.nav_host_fragment)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.app_navigation)
        graph.startDestination = R.id.previewFragment
        navController.graph = graph
    }

    override fun onDestroy() {
        super.onDestroy()
        progressBar = null
        errorMessageTextView = null
        retryButton = null
    }
}