package com.example.balinatest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.balinatest.network.ApiClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = ApiClient.getClient()

        GlobalScope.launch {
            val response = client.getPhotoTypes(0)
            Log.d("WTF", "${response.body()}")
        }
    }
}
