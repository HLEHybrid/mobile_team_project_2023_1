package com.seoultech.healthytoday

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.seoultech.healthytoday.databinding.ActivityRecommendBinding
import kotlinx.coroutines.*
import java.util.*

class RecommendActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecommendBinding
    private var firestore : FirebaseFirestore? = null
    private var uid : String? = null
    private var maxkcal : Int? = null
    private var maxprotein : Int? = null
    private var maxcarbo : Int? = null
    val surveydata = mutableListOf<String?>()
    val firstfooddata = mutableListOf<String?>()
    val secondfooddata = mutableListOf<String?>()
    val thirdfooddata = mutableListOf<String?>()


    private var max = mutableMapOf<String, Int>()
    var menu1_1 = ""
    var menu1_2 = ""
    var menu1_3 = ""
    var menu2_1 = ""
    var menu2_2 = ""

    private fun limit(age: String?, sex: String?, exception: String?): MutableMap<String, Int> {
        if((age == "10대") && (sex == "남성") && (exception == "해당사항없음")){
            maxkcal = 2500
            maxcarbo = 130
            maxprotein = 60
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            Toast.makeText(this, "칼로리: " + maxkcal.toString() + " 단백질: " + maxcarbo.toString() +
                    " 탄수화물: " + maxprotein.toString(), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
            return max
        }
        else if((age == "10대") && (sex == "여성") && (exception == "해당사항없음")){
            maxkcal = 2000
            maxcarbo = 130
            maxprotein = 55
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            Toast.makeText(this, "칼로리: " + maxkcal.toString() + " 단백질: " + maxcarbo.toString() +
                    " 탄수화물: " + maxprotein.toString(), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
            return max
        }
        else if((age == "20대") && (sex == "남성") && (exception == "해당사항없음")){
            maxkcal = 2600
            maxcarbo = 130
            maxprotein = 65
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            Toast.makeText(this, "칼로리: " + maxkcal.toString() + " 단백질: " + maxcarbo.toString() +
                    " 탄수화물: " + maxprotein.toString(), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
            return max
        }
        else if((age == "20대") && (sex == "여성") && (exception == "해당사항없음")){
            maxkcal = 2000
            maxcarbo = 130
            maxprotein = 55
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            Toast.makeText(this, "칼로리: " + maxkcal.toString() + " 단백질: " + maxcarbo.toString() +
                    " 탄수화물: " + maxprotein.toString(), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
            return max
        }
        else if(((age == "30대") ||(age == "40대")) && (sex == "남성") && (exception == "해당사항없음")){
            maxkcal = 2500
            maxcarbo = 130
            maxprotein = 65
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            Toast.makeText(this, "칼로리: " + maxkcal.toString() + " 단백질: " + maxcarbo.toString() +
                    " 탄수화물: " + maxprotein.toString(), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
            return max
        }
        else if(((age == "30대") ||(age == "40대")) && (sex == "여성") && (exception == "해당사항없음")){
            maxkcal = 1900
            maxcarbo = 130
            maxprotein = 50
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            Toast.makeText(this, "칼로리: " + maxkcal.toString() + " 단백질: " + maxcarbo.toString() +
                    " 탄수화물: " + maxprotein.toString(), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
            return max
        }
        else if((age == "50대") && (sex == "남성") && (exception == "해당사항없음")){
            maxkcal = 2200
            maxcarbo = 130
            maxprotein = 60
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            Toast.makeText(this, "칼로리: " + maxkcal.toString() + " 단백질: " + maxcarbo.toString() +
                    " 탄수화물: " + maxprotein.toString(), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
            return max
        }
        else if((age == "50대") && (sex == "여성") && (exception == "해당사항없음")){
            maxkcal = 1700
            maxcarbo = 130
            maxprotein = 50
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            Toast.makeText(this, "칼로리: " + maxkcal.toString() + " 단백질: " + maxcarbo.toString() +
                    " 탄수화물: " + maxprotein.toString(), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
            return max
        }
        else if((age == "60대 이상") && (sex == "남성") && (exception == "해당사항없음")){
            maxkcal = 2000
            maxcarbo = 130
            maxprotein = 60
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            Toast.makeText(this, "칼로리: " + maxkcal.toString() + " 단백질: " + maxcarbo.toString() +
                    " 탄수화물: " + maxprotein.toString(), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
            return max
        }
        else if((age == "60대 이상") && (sex == "여성") && (exception == "해당사항없음")){
            maxkcal = 1600
            maxcarbo = 130
            maxprotein = 50
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            Toast.makeText(this, "칼로리: " + maxkcal.toString() + " 단백질: " + maxcarbo.toString() +
                    " 탄수화물: " + maxprotein.toString(), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
            return max
        }
        else if((age == "10대") && (sex == "여성") && (exception == "임신")){
            maxkcal = 2400
            maxcarbo = 175
            maxprotein = 85
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            Toast.makeText(this, "칼로리: " + maxkcal.toString() + " 단백질: " + maxcarbo.toString() +
                    " 탄수화물: " + maxprotein.toString(), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
            return max
        }
        else if((age == "10대") && (sex == "여성") && (exception == "수유")){
            maxkcal = 2340
            maxcarbo = 210
            maxprotein = 80
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            Toast.makeText(this, "칼로리: " + maxkcal.toString() + " 단백질: " + maxcarbo.toString() +
                    " 탄수화물: " + maxprotein.toString(), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
            return max
        }
        else if((age == "20대") && (sex == "여성") && (exception == "임신")){
            maxkcal = 2400
            maxcarbo = 175
            maxprotein = 85
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            Toast.makeText(this, "칼로리: " + maxkcal.toString() + " 단백질: " + maxcarbo.toString() +
                    " 탄수화물: " + maxprotein.toString(), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
            return max
        }
        else if((age == "20대") && (sex == "여성") && (exception == "수유")){
            maxkcal = 2340
            maxcarbo = 210
            maxprotein = 80
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            Toast.makeText(this, "칼로리: " + maxkcal.toString() + " 단백질: " + maxcarbo.toString() +
                    " 탄수화물: " + maxprotein.toString(), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
            return max
        }
        else if(((age == "30대") ||(age == "40대")) && (sex == "여성") && (exception == "임신")){
            maxkcal = 2300
            maxcarbo = 175
            maxprotein = 80
            max.put("maxkcal", maxkcal!!)
            max.put("maxcarbo", maxcarbo!!)
            max.put("maxprotein", maxprotein!!)
            Toast.makeText(this, "칼로리: " + maxkcal.toString() + " 단백질: " + maxcarbo.toString() +
                    " 탄수화물: " + maxprotein.toString(), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend)
        binding = ActivityRecommendBinding.inflate(layoutInflater)

        uid = FirebaseAuth.getInstance().currentUser?.uid
        firestore = FirebaseFirestore.getInstance()
        val db = Firebase.firestore
        var sex = ""

        val docRef = firestore!!.document("survey/" + uid.toString())
        docRef.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documentSnapshot = task.result
                    try {
                        with(documentSnapshot) {
                            val age = getString("age")
                            val sex = getString("sex")
                            val exception = getString("exception")
                            val morning = getString("morning")
                            val lunch = getString("lunch")
                            val dinner = getString("dinner")
                            val lunchwhere = getString("lunch_where")
                            val dinnerwhere = getString("dinner_where")
                            surveydata.addAll(listOf(age, sex, exception, morning, lunch, dinner, lunchwhere, dinnerwhere))
                            max = limit(surveydata[0], surveydata[1], surveydata[2])
                            Log.d("IISE",surveydata.toString())
                            Log.d("IISE",max.toString())
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this, "설문조사를 다시 진행해주세요.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }
                } else {
                    Toast.makeText(this, "저장된 설문조사가 없습니다.", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
            }
//        binding.findrecommend.setOnClickListener {
//            binding.kcal.text = "칼로리: " + max.get("maxkcal").toString()
//            binding.protein.text = "단백질: " + max.get("maxprotein").toString()
//            binding.carbon.text = "탄수화물: " + max.get("maxcarbo").toString()
//            Log.d("IISE","확인 클릭")
//        }
//        binding.cooking.setOnClickListener {
//            startActivity(Intent(this@RecommendActivity, HomeMakeActivity::class.java))
//            Log.d("IISE","뭔가 클릭")
//        }
//        binding.outside.setOnClickListener {
//            startActivity(Intent(this@RecommendActivity, OutsideActivity::class.java))
//            finish()
//        }







//        val foodRef1 = firestore!!.document("fooddatabase/" + (Random().nextInt(801).toString()+1))
//        foodRef1.get()
//            .addOnSuccessListener { documentSnapshot ->
//                try {
//                    with(documentSnapshot) {
//                        val carbon = getDouble("carbon").toString()
//                        val kcal = getDouble("kcal").toString()
//                        val number = get("number").toString()
//                        val protein = getDouble("protein").toString()
//                        val name = getString("name")
//                        firstfooddata.addAll(listOf(carbon,kcal,number,protein,name))
//                        Log.d("IISE", firstfooddata.toString())
//                    }
//                } catch (e: Exception){
//                    Log.d("IISE", "$e")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("IISE", "$exception")
//            }
//
//        val foodRef2 = firestore!!.document("fooddatabase/" + (Random().nextInt(801).toString()+1))
//        foodRef2.get()
//            .addOnSuccessListener { documentSnapshot ->
//                try {
//                    with(documentSnapshot) {
//                        val carbon = getDouble("carbon").toString()
//                        val kcal = getDouble("kcal").toString()
//                        val number = get("number").toString()
//                        val protein = getDouble("protein").toString()
//                        val name = getString("name")
//                        secondfooddata.addAll(listOf(carbon,kcal,number,protein,name))
//                        Log.d("IISE", secondfooddata.toString())
//                    }
//                } catch (e: Exception){
//                    Log.d("IISE", "$e")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("IISE", "$exception")
//            }
//
//        val foodRef3 = firestore!!.document("fooddatabase/" + (Random().nextInt(801).toString()+1))
//        foodRef3.get()
//            .addOnSuccessListener { documentSnapshot ->
//                try {
//                    with(documentSnapshot) {
//                        val carbon = getDouble("carbon").toString()
//                        val kcal = getDouble("kcal").toString()
//                        val number = get("number").toString()
//                        val protein = getDouble("protein").toString()
//                        val name = getString("name")
//                        thirdfooddata.addAll(listOf(carbon,kcal,number,protein,name))
//                        Log.d("IISE", thirdfooddata.toString())
//                    }
//                } catch (e: Exception){
//                    Log.d("IISE", "$e")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("IISE", "$exception")
//            }





//        val docRef = firestore!!.document("survey/" + uid.toString())
//        docRef.get()
//            .addOnSuccessListener { documentSnapshot ->
//                try {
//                    with(documentSnapshot) {
//                        val age = getString("age")
//                        val sex = getString("sex")
//                        val exception = getString("exception")
//                        val morning = getString("morning")
//                        val lunch = getString("lunch")
//                        val dinner = getString("dinner")
//                        val lunchwhere = getString("lunch_where")
//                        val dinnerwhere = getString("dinner_where")
//                        surveydata.addAll(listOf(age, sex, exception, morning, lunch, dinner, lunchwhere, dinnerwhere))
//                        max = limit(surveydata[0], surveydata[1], surveydata[2])
//                    }
//                } catch (e: Exception){
//                    Toast.makeText(this, "설문조사를 다시 진행해주세요.", Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(this, HomeActivity::class.java))
//                    finish()
//                }
//            }
//            .addOnFailureListener { exception ->
//                Toast.makeText(this, "저장된 설문조사가 없습니다.", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this,HomeActivity::class.java))
//                finish()
//            }



//        if (surveydata.size >= 6 && surveydata[3] == "O" && surveydata[4] == "O" && surveydata[5] == "O"){
//
//            menu1_1 = foodname[firstfooddata[2]?.toInt()!!]
//            menu1_2 = foodname[secondfooddata[2]?.toInt()!!]
//            menu1_3 = foodname[thirdfooddata[2]?.toInt()!!]
//            Log.d("IISE1",menu1_1)
//            binding.morning.text = "아침: $menu1_1"
//            binding.lunch.text = "점심: $menu1_2"
//            binding.dinner.text = "저녁: $menu1_3"
//        }
//        else if (surveydata.size >= 4 && surveydata[3] != "O"){
//            menu2_1 = foodname[firstfooddata[2]?.toInt()!!]
//            menu2_2 = foodname[secondfooddata[2]?.toInt()!!]
//            binding.lunch.text = "점심: $menu2_1"
//            binding.dinner.text = "저녁: $menu2_2"
//        }
//        else if (surveydata.size >= 5 && surveydata[4] != "O"){
//            menu2_1 = foodname[firstfooddata[2]?.toInt()!!]
//            menu2_2 = foodname[secondfooddata[2]?.toInt()!!]
//            binding.lunch.text = "아침: $menu2_1"
//            binding.dinner.text = "저녁: $menu2_2"
//        }
//        else if (surveydata.size >= 6 && surveydata[5] != "O"){
//            menu2_1 = foodname[firstfooddata[2]?.toInt()!!]
//            menu2_2 = foodname[secondfooddata[2]?.toInt()!!]
//            binding.lunch.text = "아침: $menu2_1"
//            binding.dinner.text = "점심: $menu2_2"
//        }
//        else{
//            Log.d("IISE","망했는데요?")
//        }



    }
}





