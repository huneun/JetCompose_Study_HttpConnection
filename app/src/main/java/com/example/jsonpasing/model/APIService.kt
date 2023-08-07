package com.example.jsonpasing.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    fun getData(@Url url : String): Call<TestEntry>
}