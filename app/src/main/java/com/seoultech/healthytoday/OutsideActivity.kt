package com.seoultech.healthytoday

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outside)
        val button = findViewById<Button>(R.id.button)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getCurrentLocation { latitude, longitude ->
            // Do something with latitude and longitude
            searchRestaurants(latitude, longitude)

        }

        /*구글 맵 열고 화면 가져오기
        val intent = Intent(this, MapsActivityCurrentPlace::class.java)
        startActivity(intent)
        */
        button.setOnClickListener {
            Log.d("test","${food_shop[0]}")
            var place_to_go :String ="sd"
            place_to_go=food_shop[0].getString("name")
            showPlaceOnGoogleMap(place_to_go)
            // showCurrentPlace() 메서드 호출 예시
            // mapsActivityCurrentPlace.showCurrentPlace()

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            } else {
            }
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
                val responseBody = response?.body()?.string()
                if (response != null) {
                    if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
                        val jsonObject = JSONObject(responseBody)
                        val resultsArray = jsonObject.getJSONArray("results")

                        val list = mutableListOf<JSONObject>()
                        for (i in 0 until resultsArray.length()) {
                            val jsonItem = resultsArray.getJSONObject(i)
                            list.add(jsonItem)
                        }
                        food_shop = list
                        //test code
                        for (i in 0 until resultsArray.length()) {
                            val result = resultsArray.getJSONObject(i)
                            val name = result.getString("name")
                            val vicinity = result.getString("vicinity")

                            // Process restaurant information
                            Log.d("json call test","Name: $name, Address: $vicinity")
                        }
                    } else {
                        // Handle response failure
                    }
                }
            }

        })
    }
    public fun getCurrentLocation(callback: (latitude: Double, longitude: Double) -> Unit) {
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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
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

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }
}
