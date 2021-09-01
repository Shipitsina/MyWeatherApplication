package ru.gb.shipitsina.myweatherapplication.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val liveDataToObserve: MutableLiveData<String> = MutableLiveData()

    fun getData(): LiveData<String>{
        getDataFromLocalSource()
        return liveDataToObserve
    }

    private fun getDataFromLocalSource(){
        Thread{
            Thread.sleep(5000)
            liveDataToObserve.postValue("Новые данные")
            Thread.sleep(5000)
            liveDataToObserve.postValue("Совсем новые данные")
            Thread.sleep(5000)
            liveDataToObserve.postValue("Новее некуда данные")
        }.start()
    }
}