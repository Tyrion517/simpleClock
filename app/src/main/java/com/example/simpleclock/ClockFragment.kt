package com.example.simpleclock

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import com.example.simpleclock.internet.RecordFetcher
import java.text.DateFormat
import java.util.Date
import java.util.concurrent.Executor
import java.util.concurrent.Executors


private const val TAG = "ClockFragment"
class ClockFragment: Fragment() {


    private lateinit var timeTextView: TextView
    private lateinit var recordListButton: Button
    private lateinit var recordTimeButton: Button
    private lateinit var fetchRecordButton: Button
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
        fetchRecordButton = view.findViewById(R.id.fetch_record_button) as Button


        recordTimeButton.setOnClickListener{
            recordTime()
        }

        fetchRecordButton.setOnClickListener {
            fetchRecords()
            fetchRecordButton.visibility = View.INVISIBLE
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



    private fun recordTime(){
        val record = Record(timeFormat.format(Date()))
        repository.addRecord(record)
    }

    private fun fetchRecords(){
        val datesLiveData: LiveData<List<Date>> = RecordFetcher().fetchContents()
        // 将LiveData<List<Date>>映射为LiveData<List<Record>>
        val recordsResponse: LiveData<List<Record>> = datesLiveData.map { dates ->
            Log.d(TAG, "Mapping begins")
            dates.map { date ->
                // 为了方便验证，特地将取回的时间按Date.toString的格式显示
                Record(date.toString())
            }
        }
        recordsResponse.observe(
            viewLifecycleOwner,
            Observer {
                Log.d(TAG, "map finished,${recordsResponse.value}")
                recordsResponse.value?.let {  repository.addRecords(it) }
                Toast.makeText(this.context, "Records Received", Toast.LENGTH_SHORT).show()
            }
        )
    }


}