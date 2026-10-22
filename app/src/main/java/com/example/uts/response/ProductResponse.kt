package com.example.uts.response

import com.example.uts.model.ProductItem
import com.google.gson.annotations.SerializedName

data class ProductResponse(
	@field:SerializedName("products")
	val products: List<ProductItem>,

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("skip")
	val skip: Int,

	@field:SerializedName("limit")
	val limit: Int
)