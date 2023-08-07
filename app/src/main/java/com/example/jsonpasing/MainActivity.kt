package com.example.jsonpasing

import android.app.Person
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.jsonpasing.model.TestEntry
import com.example.jsonpasing.ui.theme.JsonPasingTheme
import com.example.jsonpasing.viewmodel.PetViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*;
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.net.URL
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JsonPasingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}



@Composable
fun Greeting(viewModel: PetViewModel = viewModel()) {

    Column {
        Button(onClick = { viewModel.fetchDataAndUpdateUI() }) {
            Text(text = "Fetch Data")
        }

        val list : List<TestEntry> = viewModel.dataState.value.orEmpty()

        if(!list.isNullOrEmpty()) {
            for(entry in list) {
                Text(text = "Date is ${entry.date}, Numbering is ${entry.intake}")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JsonPasingTheme {
        Greeting()
    }
}