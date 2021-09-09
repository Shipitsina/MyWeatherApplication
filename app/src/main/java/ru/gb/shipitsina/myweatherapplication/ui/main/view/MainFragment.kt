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

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MainAdapter


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

        adapter = MainAdapter()
        adapter.listener =
            MainAdapter.OnItemViewClickListener { weather ->
                val manager = activity?.supportFragmentManager
                if (manager != null){
                    val bundle = Bundle()
                    bundle.putParcelable(DetailFragment.WEATHER_EXTRA, weather)
                    manager.beginTransaction()
                        .replace(R.id.container, DetailFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commit()
                }
            }
        binding.recyclerview.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener {
        viewModel.onLanguageChange()
        }
        /*binding.mainFragmentFAB.setOnClickListener {
            isRus = !isRus
            if (isRus) {
                binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
            } else {
                binding.mainFragmentFAB.setImageResource(R.drawable.ic_world)
            }
            viewModel.getWeatherFromLocalSource(isRus)
        }*/
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.liveData.observe(viewLifecycleOwner, { state ->
            renderData(state)
        })

        viewModel.liveDataIsRus.observe(viewLifecycleOwner,{isRus ->
                if (isRus) {
                    binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
                } else {
                    binding.mainFragmentFAB.setImageResource(R.drawable.ic_world)
                }
                viewModel.getWeatherFromLocalSource()
        })
    }

    private fun renderData(state: AppState) {
        when (state) {
            is AppState.Loading -> binding.loadingLoyalt.visibility = View.VISIBLE
            is AppState.Success -> {
                binding.loadingLoyalt.visibility = View.GONE
                adapter.weatherData = state.weather
            }
            is AppState.Error -> {
                binding.loadingLoyalt.visibility = View.GONE
                Snackbar
                    .make(
                        binding.mainFragmentFAB,
                        "Error: ${state.error}",
                        Snackbar.LENGTH_INDEFINITE
                    )
                    .setAction("Reload") { viewModel.getWeatherFromLocalSource() }
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}