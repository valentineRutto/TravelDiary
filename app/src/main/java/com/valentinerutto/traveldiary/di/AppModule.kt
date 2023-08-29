package com.valentinerutto.traveldiary.di

import com.valentinerutto.traveldiary.App
import com.valentinerutto.traveldiary.TravelRepository
import com.valentinerutto.traveldiary.data.TravelDatabase
import com.valentinerutto.traveldiary.ui.TravelViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.Scope
import org.koin.dsl.module

val appModule = module {

    single { App.INSTANCE }

    single { TravelRepository(travelDao = database().travelDao()) }

    viewModel { TravelViewModel(get()) }

    single { TravelDatabase.getDatabase(context = androidContext()) }

}

fun Scope.database() = get<TravelDatabase>()