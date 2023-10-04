package com.example.simpleclock.database

import androidx.room.TypeConverter
import java.util.UUID

class RecordTypeConverters {

    @TypeConverter
    fun fromUUID(uuid: UUID?): String?{
        return uuid?.toString()
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID?{
        return UUID.fromString(uuid)
    }


}