package com.example.simpleclock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.DateFormat
import java.util.Date
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ClockFragment: Fragment() {


    private lateinit var timeTextView: TextView
    private val executor: Executor = Executors.newSingleThreadExecutor()

    private val timeFormat: DateFormat = DateFormat.getTimeInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_clock, container, false)

        timeTextView = view.findViewById(R.id.time_text_view) as TextView

        executor.execute {
            while (true) {
                timeTextView.post {
                    updateTime()
                }
                Thread.sleep(1000)
            }
        }

        return view
    }

    private fun updateTime() {
        timeTextView.text = timeFormat.format(Date())
    }


}