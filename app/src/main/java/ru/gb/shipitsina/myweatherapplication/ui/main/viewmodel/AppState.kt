package ru.gb.shipitsina.myweatherapplication.ui.main.viewmodel

import ru.gb.shipitsina.myweatherapplication.ui.main.model.Weather

sealed class AppState{

    data class Success(val weather: Weather): AppState()
    data class Error(val error: Throwable): AppState()
    object Loading : AppState()
}
