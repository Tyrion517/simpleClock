package com.example.simpleclock

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import java.text.DateFormat

private const val TAG = "RecordListFragment"
class RecordListFragment: Fragment() {
    private val recordListViewModel: RecordListViewModel by lazy {
        ViewModelProvider(this)[RecordListViewModel::class.java]
    }
    private lateinit var recordRecyclerView: RecyclerView
    private var adapter: RecordAdapter? = null

    private val timeFormat: DateFormat = DateFormat.getTimeInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val count = recordListViewModel.records.size
        Log.d(TAG, "Loaded number of records: $count")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_record_list, container, false)

        recordRecyclerView = view.findViewById(R.id.record_recycler_view) as RecyclerView
        recordRecyclerView.layoutManager = LinearLayoutManager(context)

        initializeAdapter()

        return view
    }


    private fun initializeAdapter() {
        val records = recordListViewModel.records
        adapter = RecordAdapter(records)
        recordRecyclerView.adapter = adapter
    }
    private inner class RecordHolder(view: View)
        :RecyclerView.ViewHolder(view){
            private lateinit var record: Record

            val timeTextView: TextView = itemView.findViewById(R.id.record_time_text_view) as TextView
            val deleteRecordButtonView: Button = itemView.findViewById(R.id.delete_record_button) as Button

            fun bind(record: Record) {
                this.record = record
                timeTextView.text = record.time
            }
    }

    private inner class RecordAdapter(var records: List<Record>)
        : RecyclerView.Adapter<RecordHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordHolder {
            val view = layoutInflater.inflate(R.layout.list_item_record, parent, false)
            return RecordHolder(view)
        }

        override fun onBindViewHolder(holder: RecordHolder, position: Int) {
            val record = records[position]
            holder.bind(record)
        }

        override fun getItemCount() = recordListViewModel.records.size

    }
    companion object {
        fun newInstance(): RecordListFragment {
            return RecordListFragment()
        }
    }
}