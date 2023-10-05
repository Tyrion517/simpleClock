package com.example.simpleclock

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.simpleclock.database.RecordDatabase
import java.util.UUID
import java.util.concurrent.Executor
import java.util.concurrent.Executors

private const val DATABASE_NAME = "record-database"
class RecordRepository private constructor(context: Context){

    private val database: RecordDatabase = Room.databaseBuilder(
        context.applicationContext,
        RecordDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val recordDao = database.recordDao()
    private val executor: Executor = Executors.newSingleThreadExecutor()

    fun getRecords(): LiveData<List<Record>> = recordDao.getRecords()
    fun getRecord(id: UUID): LiveData<Record> = recordDao.getRecord(id)

    //增减数据全用一个线程解决
    fun addRecord(record: Record){
        executor.execute {
            recordDao.addRecord(record)
        }
    }

    fun addRecords(records: List<Record>) {
        executor.execute {
            recordDao.addRecords(records)
        }
    }
    fun deleteRecord(record: Record){
        executor.execute {
            recordDao.deleteRecord(record)
        }
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