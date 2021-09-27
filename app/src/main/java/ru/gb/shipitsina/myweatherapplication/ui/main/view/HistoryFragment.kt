package ru.gb.shipitsina.myweatherapplication.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.gb.shipitsina.myweatherapplication.R
import ru.gb.shipitsina.myweatherapplication.databinding.HistoryFragmentBinding
import ru.gb.shipitsina.myweatherapplication.ui.main.viewmodel.HistoryAdapter
import ru.gb.shipitsina.myweatherapplication.ui.main.viewmodel.HistoryViewModel

class HistoryFragment : Fragment() {

    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this).get(HistoryViewModel::class.java)
    }

    private var _binding: HistoryFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.history_fragment, container, false)
        _binding = HistoryFragmentBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.adapter = adapter
        adapter.setData(viewModel.getAllHistory())


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}