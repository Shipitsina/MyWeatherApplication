package ru.gb.shipitsina.myweatherapplication.ui.main.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import ru.gb.shipitsina.myweatherapplication.R
import ru.gb.shipitsina.myweatherapplication.databinding.MainFragmentBinding
import ru.gb.shipitsina.myweatherapplication.ui.main.viewmodel.MainViewModel
import ru.gb.shipitsina.myweatherapplication.ui.main.viewmodel.AppState

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment,container,false)
        _binding = MainFragmentBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.liveData.observe(viewLifecycleOwner) { state ->
            renderData(state)
        }

        viewModel.getWeatherFromLocalSource()
    }

    private fun renderData(state: AppState) {
        when(state){
            is AppState.Loading -> binding.loadingLoyalt.visibility = View.VISIBLE
            is AppState.Success -> {
                binding.loadingLoyalt.visibility = View.GONE
                binding.message.text = "${state.weather.city.name}" +
                        "\n lat/lon ${state.weather.city.lat} ${state.weather.city.lon}" +
                        "\n температура ${state.weather.temperature} "+
                "\n ощущается как ${state.weather.feelsLike}"
            }
            is AppState.Error -> {
                binding.loadingLoyalt.visibility = View.GONE
                Snackbar
                    .make(binding.mainView, "Error: ${state.error}", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload"){ viewModel.getWeatherFromLocalSource()}
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}