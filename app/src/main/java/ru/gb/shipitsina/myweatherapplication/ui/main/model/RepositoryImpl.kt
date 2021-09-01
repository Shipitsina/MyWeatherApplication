package ru.gb.shipitsina.myweatherapplication.ui.main.model

class RepositoryImpl: Repository {
    override fun getWeatherFromServer(): Weather = Weather()

    override fun getWeatherFromLocalStorage(): List<Weather> = listOf(Weather(getDefaultCity(), 2, 3),
    Weather(getDefaultCity(), 10, 15))
}