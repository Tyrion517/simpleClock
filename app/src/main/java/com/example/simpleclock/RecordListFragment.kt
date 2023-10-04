package com.example.simpleclock

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "RecordListFragment"
class RecordListFragment: Fragment() {
    private val recordListViewModel: RecordListViewModel by lazy {
        ViewModelProvider(this)[RecordListViewModel::class.java]
    }
    private lateinit var recordRecyclerView: RecyclerView
    private var adapter: RecordAdapter? = RecordAdapter(emptyList())


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_record_list, container, false)

        //启用recycleView
        recordRecyclerView = view.findViewById(R.id.record_recycler_view) as RecyclerView
        recordRecyclerView.layoutManager = LinearLayoutManager(context)
        recordRecyclerView.adapter = adapter


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //设置observer以后，每次数据库内的数据发生变化，都会自动更新UI
        //之后对数据进行增删查改都不需要考虑完成操作后更新UI的问题了
        recordListViewModel.recordsListLiveData.observe(viewLifecycleOwner) {records ->
            records?.let {
                Log.d(TAG, "Got ${records.size} records")
                updateUI(records)
            }
        }
    }

    //用coroutine删除，以防阻塞UI
    private fun deleteRecord(record: Record){
        lifecycleScope.launch(Dispatchers.IO) {
            recordListViewModel.recordRepository.deleteRecord(record)
        }
    }

    private fun updateUI(records: List<Record>) {
        adapter = RecordAdapter(records)
        recordRecyclerView.adapter = adapter
    }
    private inner class RecordHolder(view: View)
        :RecyclerView.ViewHolder(view), View.OnClickListener {
            private lateinit var record: Record

            val timeTextView: TextView = itemView.findViewById(R.id.record_time_text_view) as TextView
            val deleteRecordButtonView: Button = itemView.findViewById(R.id.delete_record_button) as Button

            init {
                deleteRecordButtonView.setOnClickListener(this)
            }

            fun bind(record: Record) {
                this.record = record
                timeTextView.text = record.time
            }

            // RecordHolder自身作为OnClickListener,将成员方法onClick传递给Button.setOnClickListener
            override fun onClick(v: View?) {
                deleteRecord(record)
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

        override fun getItemCount() = records.size

    }


    companion object {
        fun newInstance(): RecordListFragment {
            return RecordListFragment()
        }
    }
}