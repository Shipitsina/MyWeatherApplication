package ru.gb.shipitsina.myweatherapplication.ui.main.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import com.google.android.material.snackbar.Snackbar
import ru.gb.shipitsina.myweatherapplication.R
import ru.gb.shipitsina.myweatherapplication.databinding.MainFragmentBinding
import ru.gb.shipitsina.myweatherapplication.ui.main.model.Weather
import ru.gb.shipitsina.myweatherapplication.ui.main.viewmodel.MainViewModel
import ru.gb.shipitsina.myweatherapplication.ui.main.viewmodel.AppState
import java.lang.Exception

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter: MainAdapter by lazy { MainAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        _binding = MainFragmentBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.listener =
            MainAdapter.OnItemViewClickListener { weather ->
                activity?.supportFragmentManager?.let { fragmentManager ->
                    fragmentManager.beginTransaction()
                        .replace(R.id.container, DetailFragment.newInstance(Bundle().apply {
                            putParcelable(DetailFragment.WEATHER_EXTRA, weather)
                        }))
                        .addToBackStack("")
                        .commit()
                }
            }
        binding.recyclerview.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener {
            viewModel.onLanguageChange()
        }

        viewModel.liveData.observe(viewLifecycleOwner, { state ->
            renderData(state)
        })

        viewModel.liveDataIsRus.observe(viewLifecycleOwner, { isRus ->
            if (isRus) {
                binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
            } else {
                binding.mainFragmentFAB.setImageResource(R.drawable.ic_world)
            }
            viewModel.getWeatherFromLocalSource()
        } )
    }

    private fun renderData(state: AppState) {
        when (state) {
            is AppState.Loading -> binding.loadingLoyalt.show()
            is AppState.Success -> {
                binding.loadingLoyalt.hide()
                adapter.weatherData = state.weather
            }
            is AppState.Error -> {
                binding.loadingLoyalt.hide()
                binding.mainFragmentFAB.showSnackBar(
                    "Error: ${state.error}",
                    "Reload",
                    { viewModel.getWeatherFromLocalSource() }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}