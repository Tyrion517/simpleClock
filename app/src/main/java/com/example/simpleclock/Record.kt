package com.example.simpleclock

import java.text.DateFormat
import java.util.Date
import java.util.UUID

private val timeFormat: DateFormat = DateFormat.getTimeInstance()
data class Record (val uuid: UUID=UUID.randomUUID(),
    val time: String= timeFormat.format(Date()))