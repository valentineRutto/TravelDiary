package com.valentinerutto.traveldiary.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.valentinerutto.traveldiary.data.model.TravelDetailsEntity

@Database(    entities = [
    TravelDetailsEntity::class
],

    version = 1,

    exportSchema = false)
abstract class TravelDatabase :RoomDatabase(){
    abstract fun travelDao(): TravelDao

}