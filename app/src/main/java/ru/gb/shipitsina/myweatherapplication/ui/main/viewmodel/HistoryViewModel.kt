package ru.gb.shipitsina.myweatherapplication.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import ru.gb.shipitsina.myweatherapplication.ui.main.model.LocalRepositoryImpl
import ru.gb.shipitsina.myweatherapplication.ui.main.model.database.HistoryEntity
import ru.gb.shipitsina.myweatherapplication.ui.main.view.App

class HistoryViewModel: ViewModel() {

    private val historyRepository = LocalRepositoryImpl(App.getHistoryDao())

    fun getAllHistory(): List<HistoryEntity> = historyRepository.getAllHistory()
}