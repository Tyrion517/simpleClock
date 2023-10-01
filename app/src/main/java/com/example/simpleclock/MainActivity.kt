package com.example.simpleclock

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

import java.text.DateFormat

import java.util.Date

class MainActivity : ComponentActivity() {

    private lateinit var timeTextView: TextView
    private lateinit var updateTimeButton: Button


    private val timeFormat: DateFormat = DateFormat.getTimeInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timeTextView = findViewById(R.id.time_text_view)
        updateTimeButton = findViewById(R.id.update_time_button)

        timeTextView.text = timeFormat.format(Date())

        updateTimeButton.setOnClickListener {
            updateTime()
        }

        // 新建thread来控制时间更新
        Thread {
            while (true) {
                timeTextView.post {
                    updateTime()
                }
                //间隔太短将难以验证是否阻塞主进程
                Thread.sleep(5000)
            }
        }.start()
    }

    private fun updateTime() {
        timeTextView.text = timeFormat.format(Date())
    }


}