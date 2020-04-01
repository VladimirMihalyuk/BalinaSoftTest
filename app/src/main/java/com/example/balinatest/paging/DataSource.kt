package com.example.balinatest.paging

import android.util.Log
import androidx.paging.PositionalDataSource
import com.example.balinatest.network.ApiService
import com.example.balinatest.network.data_models.ContentItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PostsDataSource(private val scope: CoroutineScope, private val apiService: ApiService) :
    PositionalDataSource<ContentItem>() {

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<ContentItem>
    ) {
        scope.launch {
            try {
                val response = apiService.getPhotoTypes(0)
                if(response.isSuccessful){
                    val listing = response.body()?.content
                    if(listing != null){
                        callback.onResult(listing, 0)
                    }
                }
            }catch (exception : Exception){
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<ContentItem>) {
        scope.launch {
            try {
                val response
                        = apiService.getPhotoTypes(params.startPosition / params.loadSize)
                if(response.isSuccessful){
                    val listing = response.body()?.content
                    if(listing != null
                        && (response.body()?.totalElements ?: Int.MAX_VALUE) > params.startPosition){
                        callback.onResult(listing)
                    }
                }
            }catch (exception : Exception){
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }
    }
}