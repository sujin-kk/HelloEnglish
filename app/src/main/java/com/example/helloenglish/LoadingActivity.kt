package com.example.helloenglish

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class LoadingActivity : AppCompatActivity() {
    var LOADING_TIME:Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        startLoading()
    }

    private fun startLoading(){
        val handler = Handler()
        handler.postDelayed({finish()}, LOADING_TIME)
    }



}