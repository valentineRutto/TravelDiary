package com.valentinerutto.traveldiary

import com.valentinerutto.traveldiary.data.TravelDao
import com.valentinerutto.traveldiary.data.model.TravelDetailsEntity

class TravelRepository(private val travelDao: TravelDao) {
    fun getAllTravelDetails() = travelDao.getTravelDetails()
    suspend fun insertDetails(details: TravelDetailsEntity) = travelDao.insert(details)
    suspend fun searchEntries(queryText:String):List<TravelDetailsEntity>
    {        return travelDao.searchEntries("%$queryText")
    }}
