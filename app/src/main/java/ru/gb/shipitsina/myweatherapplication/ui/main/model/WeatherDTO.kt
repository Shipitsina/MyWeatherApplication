package ru.gb.shipitsina.myweatherapplication.ui.main.model

data class WeatherDTO(
    val fact: FactDTO?,
) {
    data class FactDTO(
        val temp: Int?,
        val feels_Like: Int?,
        val condition: String?
    )
}