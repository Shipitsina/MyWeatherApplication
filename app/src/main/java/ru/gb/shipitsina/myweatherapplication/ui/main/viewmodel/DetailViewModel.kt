package ru.gb.shipitsina.myweatherapplication.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.shipitsina.myweatherapplication.ui.main.model.*
import ru.gb.shipitsina.myweatherapplication.ui.main.model.database.HistoryEntity
import ru.gb.shipitsina.myweatherapplication.ui.main.view.App
import java.text.ParseException
import java.util.*

const val MAIN_LINK = "https://api.weather.yandex.ru/v2/informers?"

// следит за тем, что мы сходили на сервер, показали лоадер, отобразили какие-то данные
class DetailViewModel : ViewModel() {

    private val repository: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource())
    private val localRepository: LocalRepository = LocalRepositoryImpl(App.getHistoryDao())
    private val detailsLiveData = MutableLiveData<AppState>()

    val liveData: LiveData<AppState> = detailsLiveData

    fun getWeatherFromRemoteSource(weather: Weather){
        detailsLiveData.value = AppState.Loading

        repository.getWeatherDetailFromServer(weather.city.lat,
            weather.city.lon,
            object : Callback<WeatherDTO> {


                override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                    detailsLiveData.postValue(AppState.Error(t))
                }

                override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                    response.body()?.let {
                        detailsLiveData.postValue(checkResponse(it, weather.city))
                    }
                }

            })
    }

    private fun checkResponse(response: WeatherDTO, city: City): AppState {
        val factDTO = response.fact

        return if (factDTO?.condition != null
            && factDTO.condition.isNotBlank()
            && factDTO.temp != null
            && factDTO.feels_Like != null
        ) {
            AppState.Success(
                listOf(
                    Weather(
                        city = city,
                        temperature = factDTO.temp,
                        feelsLike = factDTO.feels_Like,
                        condition = factDTO.condition
                    )
                )
            )
        } else {
            AppState.Error(ParseException("Не смог распарсить json!", 0))
        }
    }

    fun saveWeather(weather: Weather){
        localRepository.saveEntity(
            HistoryEntity(
                id = 0,
                city = weather.city.name,
                temperature = weather.temperature,
                condition = weather.condition,
                timestamp = Date().time
            )
        )
    }
}