package com.example.uts.model

import com.google.gson.annotations.SerializedName

data class ProductItem(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("category")
    val category: String,

    @field:SerializedName("price")
    val price: Double,

    @field:SerializedName("discountPercentage")
    val discountPercentage: Double,

    @field:SerializedName("rating")
    val rating: Double,

    @field:SerializedName("stock")
    val stock: Int,

    @field:SerializedName("tags")
    val tags: List<String>,

    @field:SerializedName("brand")
    val brand: String? = null,

    @field:SerializedName("dimensions")
    val dimensions: Dimensions,

    @field:SerializedName("availabilityStatus")
    val availabilityStatus: String,

    @field:SerializedName("reviews")
    val reviews: List<ReviewItem>,

    @field:SerializedName("images")
    val images: List<String>,

    @field:SerializedName("thumbnail")
    val thumbnail: String
)