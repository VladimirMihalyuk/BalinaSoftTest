package com.example.balinatest.activity

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.balinatest.R
import com.example.balinatest.paging.PhotoAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: PhotoAdapter
    private lateinit var mainViewModel: MainViewModel

    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = PhotoAdapter(){it ->
            mainViewModel.setLatId(it)
            dispatchTakePictureIntent()
        }

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        list.adapter = adapter

        mainViewModel.getPosts().observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?) {
        super.onActivityResult(requestCode,resultCode,data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap?

            if(imageBitmap != null){

                mainViewModel.loadImage(imageBitmap)
            }
        }
    }

}
