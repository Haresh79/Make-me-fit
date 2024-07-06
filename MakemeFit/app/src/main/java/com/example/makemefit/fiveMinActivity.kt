package com.example.makemefit

import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class fiveMinActivity : AppCompatActivity() {
    var yogaTime=0L
    var workoutTime=0L
    private lateinit var timer:CountDownTimer
    private lateinit var imgView:ImageView
    val scope= CoroutineScope(Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_five_min)
        val textViewTimer=findViewById<TextView>(R.id.timer)
        imgView=findViewById(R.id.imageView3)
        val timeDuration=300000L


        val mode=intent.getIntExtra("key",0)
        if (mode==1){
            findViewById<TextView>(R.id.textView4).text="YOGA"
            scope.launch {
                while (isActive){
                    yogaSteps(yogaTime)
                    delay(1000L)
                }
            }

        }else{
            findViewById<TextView>(R.id.textView4).text="WORK OUT"
            scope.launch {
                while (isActive){
                    workoutSteps(workoutTime)
                    delay(1000L)
                }
            }
        }

        timer=object :CountDownTimer(timeDuration, 1000){
            override fun onTick(millisUntilFinished: Long) {

                yogaTime=millisUntilFinished
                workoutTime=millisUntilFinished

                val remainingSec=millisUntilFinished/1000
                val minutes=remainingSec/60
                val seconds=remainingSec%60
                val formatedTime=String.format("%02d:%02d", minutes, seconds)
                textViewTimer.text=formatedTime
            }

            override fun onFinish() {
                textViewTimer.text="FINISH"
            }

        }
        timer.start()
    }
    private fun yogaSteps(yogaTime:Long){
        when(yogaTime){
            in 0..30000 -> imgView.setImageResource(R.raw.yoga1)
            in 30001..60000 -> imgView.setImageResource(R.raw.yoga2)
            in 60001..90000 -> imgView.setImageResource(R.raw.yoga3)
            in 90001..120000 -> imgView.setImageResource(R.raw.yoga4)
            in 120001..150000 -> imgView.setImageResource(R.raw.yoga5)
            in 150001..180000 -> imgView.setImageResource(R.raw.yoga6)
            in 180001..210000 -> imgView.setImageResource(R.raw.yoga7)
            in 210001..240000 -> imgView.setImageResource(R.raw.yoga8)
            in 240001..270000 -> imgView.setImageResource(R.raw.yoga9)
            in 270001..300000 -> imgView.setImageResource(R.raw.yoga10)
            else -> imgView.setImageResource(R.drawable.yoga)
        }
    }
    private fun workoutSteps(workoutTime:Long){
        when(workoutTime){
            in 0..60000 -> imgView.setImageResource(R.raw.wo1)
            in 60001..120000 -> imgView.setImageResource(R.raw.wo2)
            in 120001..180000 -> imgView.setImageResource(R.raw.wo3)
            in 180001..240000 -> imgView.setImageResource(R.raw.wo4)
            in 240001..300000 -> imgView.setImageResource(R.raw.wo5)
            else -> imgView.setImageResource(R.drawable.exercise)
        }
    }
}