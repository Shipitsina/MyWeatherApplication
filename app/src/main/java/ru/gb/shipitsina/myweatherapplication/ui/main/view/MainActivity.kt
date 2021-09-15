package ru.gb.shipitsina.myweatherapplication.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.gb.shipitsina.myweatherapplication.R
import ru.gb.shipitsina.myweatherapplication.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private val binding: MainActivityBinding by lazy{
        MainActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}