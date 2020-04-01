package com.example.balinatest


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.balinatest.network.ApiClient
import com.example.balinatest.network.data_models.ContentItem
import com.example.balinatest.paging.PostsDataSource
import androidx.lifecycle.viewModelScope

class MainViewModel : ViewModel() {
    private var postsLiveData  : LiveData<PagedList<ContentItem>>
    private   val client = ApiClient.getClient()


    init {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .build()
        postsLiveData  = initializedPagedListBuilder(config).build()
    }

    fun getPosts():LiveData<PagedList<ContentItem>> = postsLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, ContentItem> {

        val dataSourceFactory = object : DataSource.Factory<Int, ContentItem>() {
            override fun create(): DataSource<Int, ContentItem> {
                return PostsDataSource(viewModelScope, client)
            }
        }
        return LivePagedListBuilder<Int, ContentItem>(dataSourceFactory, config)
    }
}