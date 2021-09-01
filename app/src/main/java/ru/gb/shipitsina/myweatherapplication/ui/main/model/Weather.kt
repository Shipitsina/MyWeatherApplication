package ru.gb.shipitsina.myweatherapplication.ui.main.model

data class Weather (
    val city: City=getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0
)

fun getDefaultCity(): City = City("Москва",55.7522,37.6156)
