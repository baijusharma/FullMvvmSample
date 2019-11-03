package com.fullmvvmsample.baiju.data.db.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Quote(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val author: String,
    val quote: String,
    val thumbnail: String,
    @SerializedName("updated_at") //original name
    val updatedAt: String,
    @SerializedName("created_at") //original name
    val createdAt: String
)