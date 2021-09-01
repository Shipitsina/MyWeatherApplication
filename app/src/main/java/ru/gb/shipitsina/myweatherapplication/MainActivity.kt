package ru.gb.shipitsina.myweatherapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.gb.shipitsina.myweatherapplication.databinding.MainActivityBinding
import ru.gb.shipitsina.myweatherapplication.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding:MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}