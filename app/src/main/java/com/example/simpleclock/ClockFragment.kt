package com.example.simpleclock

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Date
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ClockFragment: Fragment() {


    private lateinit var timeTextView: TextView
    private lateinit var recordListButton: Button
    private lateinit var recordTimeButton: Button
    private val updateTimeExecutor: Executor = Executors.newSingleThreadExecutor()
    private val repository: RecordRepository by lazy {
        RecordRepository.get()
    }

    private val timeFormat: DateFormat = DateFormat.getTimeInstance()

    interface Callbacks{
        fun onRecordListButtonClicked()
    }

    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_clock, container, false)

        timeTextView = view.findViewById(R.id.time_text_view) as TextView
        recordListButton = view.findViewById(R.id.record_list_button) as Button
        recordTimeButton = view.findViewById(R.id.record_time_button) as Button

        recordTimeButton.setOnClickListener{
            recordTime()
        }

        recordListButton.setOnClickListener{
            callbacks?.onRecordListButtonClicked()
        }

        //启用executor比较耗时，在启用前先刷新一次
        updateTime()

        //用于异步刷新时间
        updateTimeExecutor.execute {
            while (true) {
                timeTextView.post {
                    updateTime()
                }
                Thread.sleep(1000)
            }
        }

        return view
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateTime() {
        timeTextView.text = timeFormat.format(Date())
    }


    //用coroutine比单开一个线程开销更小
    private fun recordTime(){
        val record = Record(timeFormat.format(Date()))
        lifecycleScope.launch(Dispatchers.IO) {
            repository.addRecord(record)
        }
    }


}