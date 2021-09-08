package ru.gb.shipitsina.myweatherapplication.ui.main.model

interface Repository {
    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocalStorage(): List<Weather>
}