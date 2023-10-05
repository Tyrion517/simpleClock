package com.example.simpleclock.internet

import retrofit2.Call
import retrofit2.http.GET

interface RecordApi {
    @GET("/timelist")
    fun fetchContents(): Call<RecordsResponse>
}