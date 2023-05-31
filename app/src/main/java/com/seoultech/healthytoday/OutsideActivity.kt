package com.seoultech.healthytoday

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.net.PlacesClient
import com.squareup.okhttp.*

import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class OutsideActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var placesClient: PlacesClient
    var storedLatitude: Double = 0.0
    var storedLongitude: Double = 0.0
    var food_shop: List<JSONObject> = emptyList()
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

    private val apiKey = "YOUR_API_KEY" // Google Places API 키를 여기에 넣어주세요
    private val radius = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outside)
        val button = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)

        val resdb = ResDatabase.getInstance(applicationContext)


        val apiKey = "AIzaSyAniUdgYwZTqrj3MGYPNDE6VhWkgW-hlrE" //  API 키
        val latitude = 37.12345 // 현재 위치의 위도
        val longitude = 127.54321 // 현재 위치의 경도
        val radius = 1000 // 1km 반경 내의 장소를 검색하기 위한 반경 값 (단위: 미터)




        /*구글 맵 열고 화면 가져오기
        val intent = Intent(this, MapsActivityCurrentPlace::class.java)
        startActivity(intent)
        */
        button.setOnClickListener {
            val editText = findViewById<EditText>(R.id.edit)
            val searchText: String = editText.text.toString()
            showPlaceOnGoogleMap(searchText)

        }
        button2.setOnClickListener {
            startActivity(Intent(this,TargetActivity::class.java))
        }

    }


    fun showPlaceOnGoogleMap(name: String) {
        val Uri_to_go:String="https://www.google.com/maps/place/?q=name:"+name
        val mapIntentUri = Uri.parse(Uri_to_go)
        val mapIntent = Intent(Intent.ACTION_VIEW, mapIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        // 구글 맵 앱이 설치되어 있는지 확인
        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else {
            // 구글 맵 앱이 설치되어 있지 않은 경우, 웹 브라우저에서 열기
            val webIntent = Intent(Intent.ACTION_VIEW, mapIntentUri)
            startActivity(webIntent)
        }
    }
    fun searchRestaurants(latitude: Double, longitude: Double) {
        val apiKey = "AIzaSyAniUdgYwZTqrj3MGYPNDE6VhWkgW-hlrE" //  API 키
        val radius = 1000 // 주변 검색 반경(미터)

        val client = OkHttpClient()//http 연결

        val url = HttpUrl.Builder()
            .scheme("https")
            .host("maps.googleapis.com")
            .addPathSegment("maps")
            .addPathSegment("api")
            .addPathSegment("place")
            .addPathSegment("nearbysearch")
            .addPathSegment("json")
            .addQueryParameter("key", apiKey)
            .addQueryParameter("location", "$latitude,$longitude")
            .addQueryParameter("radius", radius.toString())
            .addQueryParameter("type", "restaurant")
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(request: Request?, e: IOException?) {
                // 요청 실패 처리
            }

            override fun onResponse(response: Response?) {
                val button = findViewById<Button>(R.id.button)

                val responseBody = response?.body()?.string()
                if (response != null && responseBody != null) {
                    if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
                        val jsonObject = JSONObject(responseBody)
                        val resultsArray = jsonObject.getJSONArray("results")

                        val list = mutableListOf<JSONObject>()
                        for (i in 0 until resultsArray.length()) {
                            val jsonItem = resultsArray.getJSONObject(i)
                            list.add(jsonItem)
                        }
                        food_shop = list

                        // 비동기 작업이 완료된 시점에서 버튼을 활성화
                        runOnUiThread {
                            button.isEnabled = true
                        }

                        // ...
                    } else {
                        // Handle response failure
                    }
                }
            }

        })
    }
    fun getCurrentLocation(callback: (latitude: Double, longitude: Double) -> Unit) {
        val locationRequest = LocationRequest.create().apply {
            interval = 99999999999999999 // Update interval in milliseconds
            fastestInterval = 99999999999999999 // Fastest update interval in milliseconds
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationCallback = object : LocationCallback() {
            fun getLocationCoordinates(locationResult: LocationResult): Pair<Double, Double>? {
                val lastLocation = locationResult.lastLocation ?: return null
                val latitude = lastLocation.latitude
                val longitude = lastLocation.longitude
                return Pair(latitude, longitude)
            }

            override fun onLocationResult(locationResult: LocationResult) {
                val coordinates = getLocationCoordinates(locationResult)
                if (coordinates != null) {
                    val latitude = coordinates.first
                    val longitude = coordinates.second
                    storedLatitude = latitude
                    storedLongitude = longitude
                    // Use the location coordinates

                    // Invoke the callback with latitude and longitude
                    callback(latitude, longitude)
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(
                    this,
                    "Location permission denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }




    @Database(entities = [Restaurant::class], version = 1)
    abstract class ResDatabase : RoomDatabase() {
        abstract fun restaurantDao(): RestaurantDao

        companion object {
            private var instance: ResDatabase? = null

            fun getInstance(context: Context): ResDatabase {
                if (instance == null) {
                    synchronized(ResDatabase::class) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            ResDatabase::class.java,
                            "restaurant-db"
                        ).build()
                    }
                }
                return instance!!
            }
        }
    }

    @Entity(tableName = "restaurants")
    data class Restaurant(
        @PrimaryKey val name: String,
        val address: String
    )

    @Dao
    interface RestaurantDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insert(restaurant: Restaurant)

        @Query("SELECT * FROM restaurants")
        fun getAllRestaurants(): List<Restaurant>

        @Query("DELETE FROM restaurants")
        fun deleteAllRestaurants()
    }
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }
}
