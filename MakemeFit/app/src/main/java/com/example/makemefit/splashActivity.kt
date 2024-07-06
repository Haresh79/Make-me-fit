package com.example.makemefit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class splashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
//        getUser()
//        val sharedPreferences=getSharedPreferences("userInfo",Context.MODE_PRIVATE)
        val name= getUser()

        Handler(Looper.getMainLooper()).postDelayed({
            if (name!=null){
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this,loginActivity::class.java))
                finish()
            }
        }, 2000)
    }
    private fun getUser(): String? {
        val sharedPreferences=getSharedPreferences("userInfo",Context.MODE_PRIVATE)
        return sharedPreferences.getString("userName",null)
    }
}