package com.example.makemefit


import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.time.LocalDate
import kotlin.time.times


class MainActivity : AppCompatActivity(),SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var currentSteps = 0
    private var totalSteps=0
    private var isrun=false
    private lateinit var textViewSteps:TextView
    private lateinit var textViewCalories:TextView
    private lateinit var layout:LinearLayout
    private lateinit var userName:String
    private var userWeight=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        layout=findViewById(R.id.linearLayout)
        textViewSteps=findViewById(R.id.steps)
        textViewCalories=findViewById(R.id.calories)
        loadData()
        reset()
        sensorManager=getSystemService(Context.SENSOR_SERVICE) as SensorManager
        findViewById<TextView>(R.id.usernametv).text=userName

        val intent= Intent(this,fiveMinActivity::class.java)
        findViewById<ConstraintLayout>(R.id.yogalayout).setOnClickListener {
            intent.putExtra("key",1)
            startActivity(intent)
        }
        findViewById<ConstraintLayout>(R.id.workoutlayout).setOnClickListener {
            intent.putExtra("key",0)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        isrun=true
        val stepSensor=sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor!=null){
            sensorManager?.registerListener(this,stepSensor,SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
     if (isrun){
         totalSteps= event!!.values[0].toInt()
         val stepdiff=totalSteps-currentSteps
         textViewSteps.text=stepdiff.toString()
         val distance=stepdiff*0.75
         val calories=(distance.toInt())*userWeight*0.04
         textViewCalories.text=calories.toInt().toString()+" calories"
         saveData()
     }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
    fun reset(){
        layout.setOnLongClickListener {
            totalSteps=currentSteps
            textViewSteps.text="0"
        true
        }
    }

    fun saveData(){
        val sharedPreferences=getSharedPreferences("mySteps",Context.MODE_PRIVATE)
        val editor=sharedPreferences.edit()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val timeStamp=LocalDate.now()
            val isDataPresent=sharedPreferences.getInt("${timeStamp}",0)
            if (isDataPresent==0){
                editor.putInt("$timeStamp",totalSteps)
            }

        }
        editor.apply()
    }
    fun loadData(){
        val sharedPreferences=getSharedPreferences("userInfo",Context.MODE_PRIVATE)
        val sharedPreferences2=getSharedPreferences("mySteps",Context.MODE_PRIVATE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val getSteps= sharedPreferences2.getInt("${LocalDate.now()}",0)
            currentSteps=getSteps
        }

//        val sharedPreferences2=getSharedPreferences("mySteps",Context.MODE_PRIVATE)
        userName= sharedPreferences.getString("userName","User").toString()
        userWeight=(sharedPreferences.getString("userWeight","60")!!).toInt()
    }
}