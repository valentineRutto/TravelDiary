package com.valentinerutto.traveldiary

import androidx.lifecycle.LiveData
import com.valentinerutto.traveldiary.data.TravelDao
import com.valentinerutto.traveldiary.data.model.TravelDetailsEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TravelRepositoryTest {
    private val dao = mockk<TravelDao>(relaxed = true)
    private val repo = TravelRepository(dao)
    fun setUp() {
    }

    fun tearDown() {
    }

    fun getAllTravelDetails() {
        val title = "work it out"

//coEvery { dao.getTravelDetails() } coAnswers {buildTravelDetails()}
    }
@Test
    fun insertDetails()  {
        val details = buildTravelDetails()
        val captor = slot<TravelDetailsEntity>()
        coVerify{  repo.insertDetails(capture(captor))}
        Assert.assertEquals(details.title, captor.captured.title)
}
    companion object{
        fun buildTravelDetails(): TravelDetailsEntity =   TravelDetailsEntity(
            1,
            "work it out",
            3,
            location = "nairobi",
            photo = ",",
            notes = "--",
            date = 12220
        )
    }
}