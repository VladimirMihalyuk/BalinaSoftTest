package com.example.balinatest.network.data_models

import com.google.gson.annotations.SerializedName


data class ApiResponse(

	@field:SerializedName("totalPages")
	val totalPages: Int? = null,

	@field:SerializedName("pageSize")
	val pageSize: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("content")
	val content: List<ContentItem?>? = null,

	@field:SerializedName("totalElements")
	val totalElements: Int? = null
)