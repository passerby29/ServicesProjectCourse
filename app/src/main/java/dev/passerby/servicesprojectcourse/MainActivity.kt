package dev.passerby.servicesprojectcourse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.passerby.servicesprojectcourse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.simpleService.setOnClickListener {
            startService(MyService.newIntent(this))
        }
    }
}