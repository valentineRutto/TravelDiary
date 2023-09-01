package com.valentinerutto.traveldiary.data.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "travel_table")
data class TravelDetailsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val photo: String?,
    val notes: String,
    val location: String?,
    val date: String
)