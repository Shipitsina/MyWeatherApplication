package ru.gb.shipitsina.myweatherapplication.ui.main.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class WeatherDTO(
    val fact: FactDTO?,
) {
    @Parcelize
    data class FactDTO(
        val temp: Int?,
        val feels_Like: Int?,
        val condition: String?
    ): Parcelable
}