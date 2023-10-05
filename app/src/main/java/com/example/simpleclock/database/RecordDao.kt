package com.example.simpleclock.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.simpleclock.Record
import java.util.UUID

@Dao
interface RecordDao {

    @Query("SELECT * FROM record")
    fun getRecords(): LiveData<List<Record>>

    @Query("SELECT * FROM record WHERE id=(:id)")
    fun getRecord(id: UUID): LiveData<Record>

    @Insert
    fun addRecord(record: Record)

    @Insert
    fun addRecords(records: List<Record>)

    @Delete
    fun deleteRecord(record: Record)
}