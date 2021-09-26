package ru.gb.shipitsina.myweatherapplication.ui.main.model

import retrofit2.Callback


class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource): DetailsRepository{

    override fun getWeatherDetailFromServer(lat: Double, lon: Double, callback: Callback<WeatherDTO>){
        remoteDataSource.getWeatherDetail(lat, lon, callback)
    }
}