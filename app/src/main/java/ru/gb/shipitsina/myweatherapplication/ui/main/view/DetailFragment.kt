package ru.gb.shipitsina.myweatherapplication.ui.main.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import ru.gb.shipitsina.myweatherapplication.R
import ru.gb.shipitsina.myweatherapplication.databinding.DetailFragmentBinding
import ru.gb.shipitsina.myweatherapplication.databinding.MainFragmentBinding
import ru.gb.shipitsina.myweatherapplication.ui.main.model.Weather
import ru.gb.shipitsina.myweatherapplication.ui.main.viewmodel.MainViewModel
import ru.gb.shipitsina.myweatherapplication.ui.main.viewmodel.AppState

class DetailFragment : Fragment() {

    companion object {
        const val WEATHER_EXTRA = "WEATHER_EXTRA"
        fun newInstance(bundle: Bundle): DetailFragment = DetailFragment().apply {
            arguments = bundle
        }
    }

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.detail_fragment, container, false)
        _binding = DetailFragmentBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Weather>(WEATHER_EXTRA)?.let { weather ->
            weather.city.also { city ->
                binding.city.text = city.name
                binding.lat.text = city.lat.toString()
                binding.lon.text = city.lon.toString()
            }
            with(binding){
                temperature.text = weather.temperature.toString()
                feelsLike.text = weather.feelsLike.toString()}
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}