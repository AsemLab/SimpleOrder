package com.asemlab.simpleorder.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Keep
@Entity
data class Category(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String? = null
)