package com.example.simpleclock

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.simpleclock.ui.theme.SimpleClockTheme
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : ComponentActivity() {

    private lateinit var timeTextView: TextView
    private lateinit var updateTimeButton: Button

    val timeFormat = DateFormat.getTimeInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timeTextView = findViewById(R.id.time_text_view)
        updateTimeButton = findViewById(R.id.update_time_button)

        timeTextView.text = timeFormat.format(Date())

        updateTimeButton.setOnClickListener {
            timeTextView.text = timeFormat.format(Date())
        }
    }


}