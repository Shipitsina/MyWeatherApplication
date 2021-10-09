package ru.gb.shipitsina.myweatherapplication.ui.main.model



interface DetailsRepository {

    fun getWeatherDetailFromServer(lat: Double, lon: Double, callback: retrofit2.Callback<WeatherDTO>)
}