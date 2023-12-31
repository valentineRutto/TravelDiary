package com.valentinerutto.traveldiary.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.valentinerutto.traveldiary.data.model.TravelDetailsEntity
import com.valentinerutto.traveldiary.util.BaseDao

@Dao
interface TravelDao : BaseDao<TravelDetailsEntity>{
    @Query("SELECT * FROM travel_table")
     fun getTravelDetails(): LiveData<List<TravelDetailsEntity>>
    @Query(
        """
    SELECT * FROM travel_table WHERE
    title LIKE :queryText
    """
    )
    suspend fun searchEntries(queryText: String): List<TravelDetailsEntity>

}