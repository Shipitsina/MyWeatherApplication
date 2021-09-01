package ru.gb.shipitsina.myweatherapplication.ui.main

sealed class AppState{

    data class Success(val weather: String):AppState()
    data class Error(val error: Throwable):AppState()
    object Loading : AppState()
}
