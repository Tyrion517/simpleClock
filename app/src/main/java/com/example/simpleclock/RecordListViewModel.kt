package com.example.simpleclock

import androidx.lifecycle.ViewModel

class RecordListViewModel: ViewModel() {
    val recordRepository: RecordRepository = RecordRepository.get()
    //Dao->Repository->ViewModel过程中，每一步都是LiveData,不会卡UI
    val recordsListLiveData = recordRepository.getRecords()
}