package ru.gb.shipitsina.myweatherapplication.ui.main.model

import ru.gb.shipitsina.myweatherapplication.ui.main.model.database.HistoryDao
import ru.gb.shipitsina.myweatherapplication.ui.main.model.database.HistoryEntity

class LocalRepositoryImpl(
    private val dao:HistoryDao
) : LocalRepository{
    override fun getAllHistory(): List<HistoryEntity> = dao.all()

    override fun saveEntity(weather: HistoryEntity) {
        dao.insert(weather)
    }

}