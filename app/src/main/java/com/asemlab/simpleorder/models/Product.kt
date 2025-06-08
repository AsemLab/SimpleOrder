package com.asemlab.simpleorder.models

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Product(
    @SerializedName("category")
    val category: Category? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("price")
    val price: Double? = null
)