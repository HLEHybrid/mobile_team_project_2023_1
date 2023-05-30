package com.seoultech.healthytoday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.seoultech.healthytoday.databinding.ActivityHomeBinding
import com.seoultech.healthytoday.databinding.ActivitySurveyBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goToSurvey.setOnClickListener {
            startActivity(Intent(this,SurveyActivity::class.java))
            finish()
        }
        binding.recommend.setOnClickListener {
            startActivity(Intent(this,RecommendActivity::class.java))
            finish()
        }
    }
}