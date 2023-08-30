package com.valentinerutto.traveldiary.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.valentinerutto.traveldiary.data.model.TravelDetailsEntity
import com.valentinerutto.traveldiary.util.Constants

@Database(
    entities = [TravelDetailsEntity::class], version = 2,

    exportSchema = false
)
abstract class TravelDatabase : RoomDatabase() {
    abstract fun travelDao(): TravelDao

    companion object {
        @Volatile
        private var INSTANCE: TravelDatabase? = null
        fun getDatabase(context: Context): TravelDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, TravelDatabase::class.java, Constants.DB_NAME
                )
                    // Wipes and rebuilds instead of migrating if no M¬igration object.
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}