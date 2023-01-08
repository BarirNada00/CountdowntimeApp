package com.example.countdowntimerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    val START_TIME_IN_MILLIS:Long = 25 * 60 *1000
    var remainingTime:Long =START_TIME_IN_MILLIS
    var timer:CountDownTimer?=null
    var isTimeRunning=false
    val Remaining_Time ="remaining time"


    lateinit var resetTv:TextView
    lateinit var timerTv:TextView
    lateinit var startBtn:Button
    lateinit var pb:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timerTv=findViewById(R.id.timer_tv)
        resetTv=findViewById(R.id.reset_tv)
        startBtn=findViewById(R.id.start_btn)
        pb=findViewById(R.id.progressBar)
        startBtn.setOnClickListener {
            if (!isTimeRunning) {
                startTimer(START_TIME_IN_MILLIS)
            }
        }
        resetTv.setOnClickListener {
             resetTimer()
        }
        }

    private fun startTimer(startTime:Long) {
         timer = object : CountDownTimer(startTime, 1 * 1000) {
            override fun onTick(timeLeft: Long) {
                remainingTime=timeLeft
                updateTimerText()
                pb.progress=remainingTime.toDouble().div(START_TIME_IN_MILLIS.toDouble()).times(100).toInt()
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Finish !! ", Toast.LENGTH_SHORT).show()
                isTimeRunning=false
            }
        }.start()
        isTimeRunning= true
    }
    private fun resetTimer(){
        timer?.cancel()
        remainingTime= START_TIME_IN_MILLIS
        updateTimerText()
        isTimeRunning=false
        pb.progress=100
    }
    private fun updateTimerText(){
        val minute= remainingTime.div(1000).div(60)
        val second=remainingTime.div(1000)%60
        val formattedTime=String.format("%02d : %02d",minute,second)
        timerTv.text=formattedTime
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(Remaining_Time,remainingTime)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedTime= savedInstanceState.getLong(Remaining_Time)
        if (savedTime!=START_TIME_IN_MILLIS)
          startTimer(savedTime)
    }
}
