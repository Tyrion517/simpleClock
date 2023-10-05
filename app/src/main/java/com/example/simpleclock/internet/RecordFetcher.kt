package com.example.simpleclock.internet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date

private const val TAG = "RecordFetcher"
class RecordFetcher {
    private val recordApi: RecordApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://632a6f811090510116c05b32.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        recordApi = retrofit.create(RecordApi::class.java)
    }



    fun fetchContents(): LiveData<List<Date>> {
        val responseLiveData: MutableLiveData<List<Date>> = MutableLiveData()
        val totalRequest: Call<RecordsResponse> = recordApi.fetchContents()

        totalRequest.enqueue(object : Callback<RecordsResponse> {
            override fun onResponse(call: Call<RecordsResponse>, response: Response<RecordsResponse>) {
                val totalResponse: RecordsResponse? = response.body()

                responseLiveData.value = totalResponse?.timeList
                Log.d(TAG, "Response received")
            }

            override fun onFailure(call: Call<RecordsResponse>, t: Throwable) {
                Log.d(TAG, "Request failed", t)
            }

        })
        return responseLiveData
    }
}