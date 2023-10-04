package com.example.simpleclock

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.util.Date
import java.util.UUID

private val timeFormat: DateFormat = DateFormat.getTimeInstance()

@Entity
data class Record(
    val time: String = timeFormat.format(Date()),
    @PrimaryKey val id: UUID = UUID.randomUUID()
)