package com.seoultech.healthytoday

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.seoultech.healthytoday.databinding.ActivityRecommendBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class RecommendActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecommendBinding
    private var firestore : FirebaseFirestore? = null
    private var uid : String? = null
    private var maxkcal : Int? = null
    private var maxprotein : Int? = null
    private var maxcarbo : Int? = null
    private var max = mutableMapOf<String, Int>()


    private fun limit(age: String?, sex: String?, exception: String?): MutableMap<String, Int> {
        if((age == "10대") && (sex == "남성") && (exception == "해당사항없음")){
            maxkcal = 2500
            maxcarbo = 130
            maxprotein = 60
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            return max
        }
        else if((age == "10대") && (sex == "여성") && (exception == "해당사항없음")){
            maxkcal = 2000
            maxcarbo = 130
            maxprotein = 55
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            return max
        }
        else if((age == "20대") && (sex == "남성") && (exception == "해당사항없음")){
            maxkcal = 2600
            maxcarbo = 130
            maxprotein = 65
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            return max
        }
        else if((age == "20대") && (sex == "여성") && (exception == "해당사항없음")){
            maxkcal = 2000
            maxcarbo = 130
            maxprotein = 55
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            return max
        }
        else if(((age == "30대") ||(age == "40대")) && (sex == "남성") && (exception == "해당사항없음")){
            maxkcal = 2500
            maxcarbo = 130
            maxprotein = 65
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            return max
        }
        else if(((age == "30대") ||(age == "40대")) && (sex == "여성") && (exception == "해당사항없음")){
            maxkcal = 1900
            maxcarbo = 130
            maxprotein = 50
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            return max
        }
        else if((age == "50대") && (sex == "남성") && (exception == "해당사항없음")){
            maxkcal = 2200
            maxcarbo = 130
            maxprotein = 60
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            return max
        }
        else if((age == "50대") && (sex == "여성") && (exception == "해당사항없음")){
            maxkcal = 1700
            maxcarbo = 130
            maxprotein = 50
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            return max
        }
        else if((age == "60대 이상") && (sex == "남성") && (exception == "해당사항없음")){
            maxkcal = 2000
            maxcarbo = 130
            maxprotein = 60
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            return max
        }
        else if((age == "60대 이상") && (sex == "여성") && (exception == "해당사항없음")){
            maxkcal = 1600
            maxcarbo = 130
            maxprotein = 50
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            return max
        }
        else if((age == "10대") && (sex == "여성") && (exception == "임신")){
            maxkcal = 2400
            maxcarbo = 175
            maxprotein = 85
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            return max
        }
        else if((age == "10대") && (sex == "여성") && (exception == "수유")){
            maxkcal = 2340
            maxcarbo = 210
            maxprotein = 80
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            return max
        }
        else if((age == "20대") && (sex == "여성") && (exception == "임신")){
            maxkcal = 2400
            maxcarbo = 175
            maxprotein = 85
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            return max
        }
        else if((age == "20대") && (sex == "여성") && (exception == "수유")){
            maxkcal = 2340
            maxcarbo = 210
            maxprotein = 80
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            return max
        }
        else if(((age == "30대") ||(age == "40대")) && (sex == "여성") && (exception == "임신")){
            maxkcal = 2300
            maxcarbo = 175
            maxprotein = 80
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            return max
        }
        else if(((age == "30대") ||(age == "40대")) && (sex == "여성") && (exception == "수유")){
            maxkcal = 2240
            maxcarbo = 210
            maxprotein = 75
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            return max
        }
        else{
            Toast.makeText(this, "설문조사를 다시 진행해주세요.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
            return max
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend)
        binding = ActivityRecommendBinding.inflate(layoutInflater)

        uid = FirebaseAuth.getInstance().currentUser?.uid
        firestore = FirebaseFirestore.getInstance()
        val surveydata = mutableListOf<String?>()

        val docRef = firestore!!.collection("survey").document(uid.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val age = document.get("age").toString()
                    val sex = document.get("sex").toString()
                    val exception = document.get("exception").toString()
                    val morning = document.get("morning").toString()
                    val lunch = document.get("lunch").toString()
                    val dinner = document.get("dinner").toString()
                    val lunchwhere = document.get("lunch_where").toString()
                    val dinnerwhere = document.get("dinner_where").toString()
                    surveydata.addAll(listOf(age, sex, exception, morning, lunch, dinner, lunchwhere, dinnerwhere))
                    max = limit(surveydata[0], surveydata[1], surveydata[2])
                } else {
                    Toast.makeText(this, "설문조사를 다시 진행해주세요.", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,HomeActivity::class.java))
                    finish()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "저장된 설문조사가 없습니다.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,HomeActivity::class.java))
                finish()
            }
        GlobalScope.launch(Dispatchers.Main) {
            val db = withContext(Dispatchers.IO) {
                AppDatabase.getInstance(applicationContext)
            }
            val calories = withContext(Dispatchers.IO) { db?.foodDao()?.calories() }
            val protein = withContext(Dispatchers.IO) { db?.foodDao()?.protein() }
            val carbon = withContext(Dispatchers.IO) { db?.foodDao()?.carbon() }

            var menu1_1 = ""
            var menu1_2 = ""
            var menu1_3 = ""
            var menu2_1 = ""
            var menu2_2 = ""

            val caloriesSize = calories?.size ?: 0
            val carbonSize = carbon?.size ?: 0
            val proteinSize = protein?.size ?: 0

            if (caloriesSize > 0 && carbonSize > 0 && proteinSize > 0) {
                while (true) {
                    val i = Random().nextInt(73260)
                    val j = Random().nextInt(73260)
                    val k = Random().nextInt(73260)

                    val caloriesI = calories?.get(i).toString().toFloatOrNull()
                    val caloriesJ = calories?.get(j).toString().toFloatOrNull()
                    val caloriesK = calories?.get(k).toString().toFloatOrNull()

                    val carbonI = carbon?.get(i).toString().toFloatOrNull()
                    val carbonJ = carbon?.get(j).toString().toFloatOrNull()
                    val carbonK = carbon?.get(k).toString().toFloatOrNull()

                    val proteinI = protein?.get(i).toString().toFloatOrNull()
                    val proteinJ = protein?.get(j).toString().toFloatOrNull()
                    val proteinK = protein?.get(k).toString().toFloatOrNull()

                    if (
                        caloriesI != null && caloriesJ != null && caloriesK != null &&
                        carbonI != null && carbonJ != null && carbonK != null &&
                        proteinI != null && proteinJ != null && proteinK != null &&
                        (0.9 * max["maxkcal"]!! <= caloriesI + caloriesJ + caloriesK) &&
                        (1.1 * max["maxkcal"]!! >= caloriesI + caloriesJ + caloriesK) &&
                        (0.9 * max["maxcarbo"]!! <= carbonI + carbonJ + carbonK) &&
                        (1.1 * max["maxcarbo"]!! >= carbonI + carbonJ + carbonK) &&
                        (0.9 * max["maxprotein"]!! <= proteinI + proteinJ + proteinK) &&
                        (1.1 * max["maxprotein"]!! >= proteinI + proteinJ + proteinK)
                    ) {
                        menu1_1 = db?.foodDao()?.getFoodAtIndex(i).toString()
                        menu1_2 = db?.foodDao()?.getFoodAtIndex(j).toString()
                        menu1_3 = db?.foodDao()?.getFoodAtIndex(k).toString()
                        break
                    }
                }
            }


            if (caloriesSize > 0 && carbonSize > 0 && proteinSize > 0) {
                while (true) {
                    val i = Random().nextInt(73260)
                    val j = Random().nextInt(73260)

                    val caloriesI = calories?.get(i).toString().toFloatOrNull()
                    val caloriesJ = calories?.get(j).toString().toFloatOrNull()

                    val carbonI = carbon?.get(i).toString().toFloatOrNull()
                    val carbonJ = carbon?.get(j).toString().toFloatOrNull()

                    val proteinI = protein?.get(i).toString().toFloatOrNull()
                    val proteinJ = protein?.get(j).toString().toFloatOrNull()

                    if (
                        caloriesI != null && caloriesJ != null &&
                        carbonI != null && carbonJ != null &&
                        proteinI != null && proteinJ != null &&
                        (0.9 * max["maxkcal"]!! <= caloriesI + caloriesJ) &&
                        (1.1 * max["maxkcal"]!! >= caloriesI + caloriesJ) &&
                        (0.9 * max["maxcarbo"]!! <= carbonI + carbonJ) &&
                        (1.1 * max["maxcarbo"]!! >= carbonI + carbonJ) &&
                        (0.9 * max["maxprotein"]!! <= proteinI + proteinJ) &&
                        (1.1 * max["maxprotein"]!! >= proteinI + proteinJ)
                    ) {
                        menu2_1 = db?.foodDao()?.getFoodAtIndex(i).toString()
                        menu2_2 = db?.foodDao()?.getFoodAtIndex(j).toString()
                        break
                    }
                }
            }


            if (surveydata.size >= 6 && surveydata[3] == "O" && surveydata[4] == "O" && surveydata[5] == "O"){
                binding.morning.text = "아침: " + menu1_1
                binding.lunch.text = "점심: " + menu1_2
                binding.dinner.text = "저녁: " + menu1_3
            }
            else if (surveydata.size >= 4 && surveydata[3] != "O"){
                binding.lunch.text = "점심: " + menu2_1
                binding.dinner.text = "저녁: " + menu2_2
            }
            else if (surveydata.size >= 5 && surveydata[4] != "O"){
                binding.lunch.text = "아침: " + menu2_1
                binding.dinner.text = "저녁: " + menu2_2
            }
            else if (surveydata.size >= 6 && surveydata[5] != "O"){
                binding.lunch.text = "아침: " + menu2_1
                binding.dinner.text = "점심: " + menu2_2
            }

        }
        binding.cooking.setOnClickListener {
            startActivity(Intent(this,HomeMakeActivity::class.java))
            finish()
        }
        binding.outside.setOnClickListener {
            startActivity(Intent(this,OutsideActivity::class.java))
            finish()
        }

    }
}