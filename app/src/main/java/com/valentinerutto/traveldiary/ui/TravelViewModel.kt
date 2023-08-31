package com.valentinerutto.traveldiary.ui

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valentinerutto.traveldiary.TravelRepository
import com.valentinerutto.traveldiary.data.model.TravelDetailsEntity
import kotlinx.coroutines.launch


class TravelViewModel(private val travelRepository: TravelRepository) : ViewModel() {
    var mAllDetails: LiveData<List<TravelDetailsEntity>>? = null
    val _selectedTravelDetails = MutableLiveData<TravelDetailsEntity>()
    val selectedTravelDetails: LiveData<TravelDetailsEntity?>
        get() = _selectedTravelDetails
  val _selectedPhotos = MutableLiveData<List<Uri>>()
    val selectedPhotos: LiveData<List<Uri>>
        get() = _selectedPhotos

    fun insertDetails() {
        viewModelScope.launch {
            travelRepository.insertDetails(
                TravelDetailsEntity(
                    1,
                    "work it out",
                    3,
                    location = "nairobi",
                    photo = ",",
                    notes = "--",
                    date = 12220
                )
            )
        }
    }

    fun getDetails() {
        viewModelScope.launch {
            mAllDetails = travelRepository.getAllTravelDetails()
        }
    }
}