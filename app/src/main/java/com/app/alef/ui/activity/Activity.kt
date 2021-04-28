package com.app.alef.ui.activity

interface Activity {
    fun showProgressBar()
    fun hideProgressBar()
    fun hideActionBar()
    fun showActionBar()
    fun showErrorMessage(msg: String)
    fun clearErrorMessage()
    fun isErrorMessageShoved():Boolean
    fun retry()
}