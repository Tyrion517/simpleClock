package com.example.simpleclock

import androidx.lifecycle.ViewModel

class RecordListViewModel: ViewModel() {
    val records = mutableListOf<Record>()

    init {
        for (i in 0 until 50) {
            records += Record()
        }
    }
}