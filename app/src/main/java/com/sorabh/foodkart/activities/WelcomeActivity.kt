package com.sorabh.foodkart.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sorabh.foodkart.R
import kotlinx.coroutines.*

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val job = SupervisorJob()
        val scope = CoroutineScope(job + Dispatchers.Main)
        scope.launch {
            delay(2500)
            val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}