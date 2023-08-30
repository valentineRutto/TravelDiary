package com.valentinerutto.traveldiary.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valentinerutto.traveldiary.TravelRepository
import com.valentinerutto.traveldiary.data.model.TravelDetailsEntity
import kotlinx.coroutines.launch


class TravelViewModel(private val travelRepository: TravelRepository) : ViewModel() {
     var mAllDetails: LiveData<List<TravelDetailsEntity>>? = null

    fun insertDetails(){
    viewModelScope.launch {
        travelRepository.insertDetails(TravelDetailsEntity(1,"work it out", 3))
    }
}
     fun getDetails(){
         viewModelScope.launch {
           mAllDetails =  travelRepository.getAllTravelDetails()
         }
    }
}