package com.seoultech.healthytoday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.seoultech.healthytoday.databinding.ActivityMainBinding
import com.seoultech.healthytoday.databinding.ActivitySurveyBinding

class SurveyActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySurveyBinding
    private var auth: FirebaseAuth? = null
    private var firestore: FirebaseFirestore? = null

    data class ResultDTO(
        var uid: String? = null,
        var exception: String? = null,
        var age: String? = null,
        var morning: String? = null,
        var lunch: String? = null,
        var dinner: String? = null,
        var lunch_where: String? = null,
        var dinner_where: String? = null
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()

        var exception = ""
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            exception = when (checkedId) {
                R.id.q1Btn1 -> "임신"
                R.id.q1Btn2 -> "수유"
                R.id.q1Btn3 -> "해당사항없음"
                else -> ""
            }
        }

        setupSpinnerFruit()
        setupSpinnerHandler()

        var morning = ""
        binding.q3checkBox1.setOnCheckedChangeListener { _, isChecked ->
            morning = if (isChecked) "O" else "X"
        }

        var lunch = ""
        binding.q3checkBox2.setOnCheckedChangeListener { _, isChecked ->
            lunch = if (isChecked) "O" else "X"
            binding.quest4.setVisibility(android.view.View.VISIBLE)
            binding.radioGroup2.setVisibility(android.view.View.VISIBLE)
        }

        var dinner = ""
        binding.q3checkBox3.setOnCheckedChangeListener { _, isChecked ->
            dinner = if (isChecked) "O" else "X"
            binding.quest5.setVisibility(android.view.View.VISIBLE)
            binding.radioGroup3.setVisibility(android.view.View.VISIBLE)
        }

        var lunch_where = ""
        binding.radioGroup2.setOnCheckedChangeListener { _, checkedId ->
            lunch_where = when (checkedId) {
                R.id.q4Btn1 -> "집"
                R.id.q4Btn2 -> "식당"
                else -> ""
            }
        }

        var dinner_where = ""
        binding.radioGroup3.setOnCheckedChangeListener { _, checkedId ->
            dinner_where = when (checkedId) {
                R.id.q5Btn1 -> "집"
                R.id.q5Btn2 -> "식당"
                else -> ""
            }
        }

        binding.save.setOnClickListener {
            val resultDTO = ResultDTO().apply {
                uid = auth?.currentUser?.uid
                this.exception = exception
                age = binding.spinner.selectedItem?.toString()
                this.morning = morning
                this.lunch = lunch
                this.dinner = dinner
                this.lunch_where = lunch_where
                this.dinner_where = dinner_where
            }

            firestore?.collection(auth?.currentUser?.uid ?: "")?.document()?.set(resultDTO)
                ?.addOnSuccessListener {
                    Toast.makeText(this, "저장완료", Toast.LENGTH_SHORT).show()
                }
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
    }

    private fun setupSpinnerFruit() {
        val ages = resources.getStringArray(R.array.ages)
        val adapter = ArrayAdapter(this, R.layout.item_spinner, ages)
        binding.spinner.adapter = adapter
    }

    private fun setupSpinnerHandler() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // 선택한 항목의 값을 age 변수에 저장
                val age = parent?.getItemAtPosition(position)?.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}
