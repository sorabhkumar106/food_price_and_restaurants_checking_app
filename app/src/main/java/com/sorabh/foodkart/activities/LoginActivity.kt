package com.sorabh.foodkart.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sorabh.foodkart.R
import com.sorabh.foodkart.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    lateinit var loginPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        //validating login credentials
        loginPreference = getSharedPreferences("foodKartLogin", MODE_PRIVATE)
        if (loginPreference.getBoolean("isLogin", false)) {
            destination(MainActivity::class.java)
        }

        // adding hint and text to views
        addHintAndText()
        loginBinding.login = this


    }

    // running when user clicked on submit button
    fun loginBtnClicked() {
        val name = loginBinding.edtName.text
        val email = loginBinding.edtEmail.text
        val phone = loginBinding.edtPhone.text
        val password = loginBinding.edtPassword.text
        loginPreference = getSharedPreferences("foodKartLogin", MODE_PRIVATE)
        if (name?.isNotBlank() == true && email?.isNotBlank() == true && phone?.isNotBlank() == true && password?.isNotBlank() == true) {
            loginPreference.edit().putString("name", name.toString()).apply()
            loginPreference.edit().putString("email", email.toString()).apply()
            loginPreference.edit().putString("phone", phone.toString()).apply()
            loginPreference.edit().putString("password", password.toString()).apply()
            loginPreference.edit().putBoolean("isLogin", true).apply()
            loginPreference.edit().apply()
            destination(MainActivity::class.java)
        } else {
            Toast.makeText(this, "Please Enter all the field", Toast.LENGTH_SHORT).show()
        }
    }

    //transact to fragment
    private fun destination(activity: Class<MainActivity>) {
        val intent = Intent(this, activity)
        startActivity(intent)
        finish()
    }

    private fun addHintAndText() {
        loginBinding.edtName.hint ="Enter your Name"
        loginBinding.edtPhone.hint = "Enter Your Phone"
        loginBinding.edtEmail.hint = "Enter Your Email"
        loginBinding.edtPassword.hint = "Enter Your Password"
        loginBinding.btnLogin.text = "Login"
    }

    override fun onDestroy() {
        super.onDestroy()
        loginBinding.unbind()
    }
}