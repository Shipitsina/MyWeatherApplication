package ru.gb.shipitsina.myweatherapplication.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gb.shipitsina.myweatherapplication.ui.main.model.Repository
import ru.gb.shipitsina.myweatherapplication.ui.main.model.RepositoryImpl
import java.lang.Exception
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repository: Repository = RepositoryImpl()
    val liveData: LiveData<AppState> = liveDataToObserve

    fun getWeatherFromLocalSource() = getDataFromLocalSource()
    fun getWeatherFromRemoteSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource(){
        liveDataToObserve.value= AppState.Loading

        Thread{
            Thread.sleep(5000)
            if (Random.nextBoolean()){
                val weatherList = repository.getWeatherFromLocalStorage()
                for (weather in weatherList)
            liveDataToObserve.postValue(AppState.Success(weather))
            } else{
                liveDataToObserve.postValue(AppState.Error(Exception("Нет интернета")))
            }
        }.start()
    }
}