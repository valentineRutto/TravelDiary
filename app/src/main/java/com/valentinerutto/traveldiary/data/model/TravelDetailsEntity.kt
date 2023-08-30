package com.valentinerutto.traveldiary.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "travel_table")
data class TravelDetailsEntity(
    @PrimaryKey
    val id:Int, val title:String,val userId:Int,val photo:String,val notes:String,val location:String,val date:Long)