package com.example.simpleclock.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.simpleclock.Record

@Database([ Record::class], version = 1)
@TypeConverters(RecordTypeConverters::class)
abstract class RecordDatabase: RoomDatabase() {
    abstract fun recordDao(): RecordDao
}