package com.example.makemefit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class loginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        val name=findViewById<TextView>(R.id.editTextText)
        val weight=findViewById<TextView>(R.id.editTextNumberDecimal)
        findViewById<Button>(R.id.button).setOnClickListener {
            if (name.text.isNotEmpty()&&weight.text.isNotEmpty()){
                saveInfo(name.text.toString(), weight.text.toString())
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(this,"Enter Valid input",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveInfo(name: String, weight: String) {
        val sharedPreferences=getSharedPreferences("userInfo",Context.MODE_PRIVATE)
        val editor=sharedPreferences.edit()
        editor.putString("userName",name)
        editor.putString("userWeight",weight)
        editor.apply()
    }
}