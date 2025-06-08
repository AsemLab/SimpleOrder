package com.asemlab.simpleorder.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Keep
@Entity
data class Product(
    @SerializedName("category")
    val category: Category? = null,
    @SerializedName("description")
    val description: String? = null,
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("price")
    val price: Double? = null
)