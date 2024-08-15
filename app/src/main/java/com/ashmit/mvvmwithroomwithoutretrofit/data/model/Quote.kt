package com.ashmit.mvvmwithroomwithoutretrofit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote")
data class Quote (
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val text :String,
    val author : String
)