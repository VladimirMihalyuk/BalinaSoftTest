package com.example.balinatest


import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.balinatest.network.ApiClient
import com.example.balinatest.paging.PostsDataSource
import androidx.lifecycle.viewModelScope
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.example.balinatest.network.data_models.ContentItem
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import okhttp3.RequestBody
import okhttp3.MultipartBody
import okhttp3.MediaType
import retrofit2.Call
import com.example.balinatest.network.data_models.ResponsePhoto
import retrofit2.Callback
import retrofit2.Response


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var postsLiveData  : LiveData<PagedList<ContentItem>>
    private val client = ApiClient.getClient()
    private var lastId:Int =0



    fun setLatId(value: Int){
        lastId = value
    }

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .build()
        postsLiveData  = initializedPagedListBuilder(config).build()
    }

    fun getPosts():LiveData<PagedList<ContentItem>> = postsLiveData

    fun loadImage(image: Bitmap){
        val file = bitmapToFile(image)
        val fileReqBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)

        val photo = MultipartBody.Part.createFormData("photo", file.name, fileReqBody)

        val name = RequestBody.create(MediaType.parse("multipart/form-data"),
            "Vladimir Mihalyuk")

        val typeId = RequestBody.create(MediaType.parse("multipart/form-data"),
            "$lastId")


        val call = client.upload(name, photo, typeId)
        call.enqueue(object : Callback<ResponsePhoto> {
            override fun onFailure(call: Call<ResponsePhoto>?, t: Throwable?) {
                showToast("Failure")
            }
            override fun onResponse(call: Call<ResponsePhoto>?, response: Response<ResponsePhoto>?) {
                if(response?.isSuccessful == true){
                    showToast("Success")
                }else{
                    showToast("Fail")
                }
            }
        })
    }

    private fun showToast(text: String){
        Toast.makeText(getApplication<Application>().applicationContext,
            text, Toast.LENGTH_LONG).show()
    }

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, ContentItem> {

        val dataSourceFactory = object : DataSource.Factory<Int, ContentItem>() {
            override fun create(): DataSource<Int, ContentItem> {
                return PostsDataSource(viewModelScope, client)
            }
        }
        return LivePagedListBuilder<Int, ContentItem>(dataSourceFactory, config)
    }

    private fun bitmapToFile(bitmap: Bitmap): File{
        val file =  File(getApplication<Application>().applicationContext.cacheDir, "photo")
        file.createNewFile()

        val bos =  ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);

        val fos =  FileOutputStream(file)
        fos.write( bos.toByteArray())
        fos.flush()
        fos.close()
        return file
    }
}


