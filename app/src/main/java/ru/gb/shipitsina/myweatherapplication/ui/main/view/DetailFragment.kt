package ru.gb.shipitsina.myweatherapplication.ui.main.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import ru.gb.shipitsina.myweatherapplication.R
import ru.gb.shipitsina.myweatherapplication.databinding.DetailFragmentBinding
import ru.gb.shipitsina.myweatherapplication.databinding.MainFragmentBinding
import ru.gb.shipitsina.myweatherapplication.ui.main.model.*
import ru.gb.shipitsina.myweatherapplication.ui.main.viewmodel.MainViewModel
import ru.gb.shipitsina.myweatherapplication.ui.main.viewmodel.AppState
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class DetailFragment : Fragment() {

    companion object {
        const val WEATHER_EXTRA = "WEATHER_EXTRA"
        fun newInstance(bundle: Bundle): DetailFragment = DetailFragment().apply {
            arguments = bundle
        }
    }

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val localResultBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.getStringExtra(RESULT_EXTRA)) {
                SUCCESS_RESULT -> {
                    intent.getParcelableExtra<WeatherDTO.FactDTO>(
                        FACT_WEATHER_EXTRA
                    )?.let {
                        displayWeather(it)
                    }
                }

                ERROR_EMPTY_DATA_RESULT -> {
                    //todo
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(localResultBroadcastReceiver,
        IntentFilter(DETAILS_INTENT_FILTER)
        )
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(localResultBroadcastReceiver)
        super.onDestroy()
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.detail_fragment, container, false)
        _binding = DetailFragmentBinding.bind(view)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Weather>(WEATHER_EXTRA)?.let { weather ->
            weather.city.also { city ->
                binding.cityName.text = city.name
                binding.cityCoordinates.text = "${city.lat} - ${city.lon}"

                getWeather(city.lat,city.lon)
            }


//            WeatherLoader(
//                weather.city.lat,
//                weather.city.lon,
//                object : WeatherLoader.WeatherLoaderListener{
//                    override fun onLoaded(weatherDTO: WeatherDTO) {
//                     requireActivity().runOnUiThread{
//                        displayWeather(weatherDTO)
//                    }
//                }
//
//                override fun onFailed(throwable: Throwable) {
//                    requireActivity().runOnUiThread{
//                    Toast.makeText(requireContext(), throwable.localizedMessage, Toast.LENGTH_LONG).show()
//                    }
//                }
//            }).goToInternet()
        }
   }

    private fun getWeather(lat: Double, lon: Double) {
        binding.mainView.hide()
        binding.loadingLoyalt.show()

        requireActivity().startService(Intent(requireContext(), MainService::class.java).apply {
            putExtra(LATITUDE_EXTRA, lat)
            putExtra(LONGITUDE_EXTRA,lon)
        })
    }

    private fun displayWeather(fact: WeatherDTO.FactDTO){
        binding.mainView.show()
        binding.loadingLoyalt.hide()

        with(binding){
            temperatureValue.text = fact.temp.toString()
            feelsLikeValue.text = fact.feels_Like.toString()
            weatherCondition.text = fact.condition.toString()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}