package com.example.simpleclock

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.simpleclock.database.RecordDatabase
import java.util.UUID

private const val DATABASE_NAME = "record-database"
class RecordRepository private constructor(context: Context){

    private val database: RecordDatabase = Room.databaseBuilder(
        context.applicationContext,
        RecordDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val recordDao = database.recordDao()

    fun getRecords(): LiveData<List<Record>> = recordDao.getRecords()
    fun getRecord(id: UUID): LiveData<Record> = recordDao.getRecord(id)
    fun addRecord(record: Record){
        recordDao.addRecord(record)
    }
    fun deleteRecord(record: Record){
        recordDao.deleteRecord(record)
    }

    companion object {
        private var INSTANCE: RecordRepository? = null


        // 定义此函数并在Application.onCreate里调用，使得应用打开的时候就初始化数据库
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = RecordRepository(context)
            }
        }

        fun get(): RecordRepository{
            return INSTANCE ?:
            throw IllegalStateException("Database must be initialized before use")
        }
    }
}