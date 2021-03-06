package ru.gb.shipitsina.myweatherapplication.ui.main.model.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1, exportSchema = true)
abstract class HistoryDataBase: RoomDatabase() {

    abstract fun historyDao(): HistoryDao


}