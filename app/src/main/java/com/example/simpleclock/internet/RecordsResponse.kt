package com.example.simpleclock.internet

import com.google.gson.annotations.SerializedName
import java.util.Date

class RecordsResponse {
    @SerializedName("timelist")
    lateinit var timeList: List<Date>
}