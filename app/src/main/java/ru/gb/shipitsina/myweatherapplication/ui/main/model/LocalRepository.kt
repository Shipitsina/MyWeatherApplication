package ru.gb.shipitsina.myweatherapplication.ui.main.model

import ru.gb.shipitsina.myweatherapplication.ui.main.model.database.HistoryEntity

interface LocalRepository {
    fun getAllHistory(): List<HistoryEntity>
    fun saveEntity(weather: HistoryEntity)
}