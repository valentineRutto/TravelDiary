package com.valentinerutto.traveldiary

import com.valentinerutto.traveldiary.data.TravelDao


class TravelRepository(private val travelDao: TravelDao) {
    suspend fun getAllTravelDetails() = travelDao.getTravelDetails()

}