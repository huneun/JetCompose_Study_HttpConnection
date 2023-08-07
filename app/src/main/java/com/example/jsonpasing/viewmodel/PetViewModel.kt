package com.example.jsonpasing.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.remember
import com.example.jsonpasing.model.TestEntry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.net.URL

class PetViewModel : ViewModel() {
    val dataState = mutableStateOf<List<TestEntry>?>(emptyList())

    fun fetchDataAndUpdateUI(){
        viewModelScope.launch {
            val fetchedData = fetchData()
            dataState.value = fetchedData

        }
    }

    suspend fun fetchData() : List<TestEntry>? {
        return withContext(Dispatchers.IO) {
            val urlString = "http://petpal.so/ssu" // 특정 URL
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()

                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                reader.close()
                println("Request Success ${response.toString()}")

                val gson = Gson()
                val entryListType: Type? = object : TypeToken<List<TestEntry>>() {}.type
                val entrys: List<TestEntry> = gson.fromJson(response.toString(), entryListType)
                return@withContext entrys
            }else {
                return@withContext null
            }
        }

    }
}