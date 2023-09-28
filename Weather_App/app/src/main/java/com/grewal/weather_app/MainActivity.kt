package com.grewal.weather_app

import android.Manifest

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.grewal.weather_app.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso

import org.json.JSONException
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var weatherModel: ArrayList<WeatherModel>
    private lateinit var weatherAdapter: WeatherAdapter
    private lateinit var forecastModel: ArrayList<WeatherForeCastModel>
    private lateinit var foreCastAdapter: ForeCastAdapter
    private lateinit var locationManager: LocationManager
    private val PERMISSION_CODE = 1
    private lateinit var city_name: String

    override fun onCreate(savedInstanceState: Bundle?) {

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        binding = ActivityMainBinding.inflate(layoutInflater)

        weatherModel = arrayListOf()
        weatherAdapter = WeatherAdapter(this, weatherModel)
        weatherAdapter.setOnClickListener {
            model->
            openHourCasrInDetail(model.time)
        }
        forecastModel = arrayListOf()
        foreCastAdapter = ForeCastAdapter(this, forecastModel)
        foreCastAdapter.setOnClickListener {model->
            openForeCastInDetail(model.date)
        }



        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSION_CODE
            )
        }
        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        //if (location != null) {
            city_name = getCityName(location!!.longitude, location!!.latitude)
            getWeatherInfo(city_name)
            binding.textPlace.text = city_name
//        } else {
//            // Handle the case where location is null, e.g., show an error message or provide a default location
//            Toast.makeText(this, "Location not available", Toast.LENGTH_LONG).show()
//        }

        binding.searchIcon.setOnClickListener {
            val city = binding.cityInputEditText.text
            if (city!!.isEmpty()) {
                Toast.makeText(this, "Enter the city name", Toast.LENGTH_LONG).show()
            } else {
                binding.textPlace.text = city
               // city_name=city.toString()
                getWeatherInfo(city.toString())
            }
        }


        binding.weatherRecyclerView.adapter = weatherAdapter
        binding.forecastRecyclerView.adapter = foreCastAdapter


        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.isEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Please Provide the Permission", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun getWeatherInfo(cityName: String) {
        val applicationInfo = applicationContext.packageManager.getApplicationInfo(
            applicationContext.packageName,
            PackageManager.GET_META_DATA
        )

        val api_key = applicationInfo.metaData["keyValue"].toString()
        val url = "https://api.weatherapi.com/v1/forecast.json?" +
                "key=$api_key" +
                "&q=$cityName&days=7&aqi=yes&alerts=yes"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            weatherModel.clear()
            forecastModel.clear()
            try {
                val temperature = response.getJSONObject("current")
                    .getString("temp_c")
                binding.tempView.text = resources
                    .getString(R.string.temp_in_celcius, temperature)

                val isDay = response.getJSONObject("current")
                    .getInt("is_day")
                if (isDay == 0) {
                    Picasso.get()
                        .load("https://w0.peakpx.com/wallpaper/80/967/HD-wallpaper-beauty-nature-beauty-of-nature-fire-forest-moon-night-stars.jpg")
                        .into(binding.BackGroundImage)
                } else {

                    Picasso.get()
                        .load("https://e0.pxfuel.com/wallpapers/706/22/desktop-wallpaper-sunrise-mobile-beach-sunrise-iphone.jpg")
                        .into(binding.BackGroundImage)
                }

                val condition = response.getJSONObject("current")
                    .getJSONObject("condition")
                    .getString("text")
                binding.weatherCondition.text = condition

                val icon = response.getJSONObject("current")
                    .getJSONObject("condition")
                    .getString("icon")


                Picasso.get().load("https:".plus(icon)).into(binding.weatherIcon)

                val windSpeed = response.getJSONObject("current")
                    .getString("wind_kph")
                binding.windSpeed.text = resources
                    .getString(R.string.wind_speed_in_km, windSpeed)

                val humidity = response.getJSONObject("current")
                    .getString("humidity")
                binding.humidity.text = resources
                    .getString(R.string.humidity, humidity)

                val cloudy = response.getJSONObject("current")
                    .getString("cloud")
                binding.cloudy.text = resources
                    .getString(R.string.cloud, cloudy)

                val real_feel = response.getJSONObject("current")
                    .getString("feelslike_c")
                binding.realFeel.text = resources
                    .getString(R.string.real_feel, real_feel)

                val forecastObj = response.getJSONObject("forecast")
                val forecast = forecastObj.getJSONArray("forecastday").getJSONObject(0)
                val hourArray = forecast.getJSONArray("hour")
                for (i in 0 until hourArray.length()) {
                    val hourObj = hourArray.getJSONObject(i)
                    val time = hourObj.getString("time")
                    val temperature = hourObj.getString("temp_c")
                    val windSpeed = hourObj.getString("wind_kph")
                    val icon = hourObj.getJSONObject("condition").getString("icon")
                    weatherModel.add(WeatherModel(time, temperature, icon, windSpeed))

                }
                val forecastArray = forecastObj.getJSONArray("forecastday")
                for (i in 0 until forecastArray.length()) {
                    val dayObj = forecastArray.getJSONObject(i)
                    val date = dayObj.getString("date")
                    val condition =
                        dayObj.getJSONObject("day").getJSONObject("condition").getString("text")
                    val icon =
                        dayObj.getJSONObject("day").getJSONObject("condition").getString("icon")
                    forecastModel.add(
                        WeatherForeCastModel(
                            date = date,
                            icon = icon,
                            condition = condition
                        )
                    )
                }


            } catch (e: JSONException) {
                e.printStackTrace()
            }
            weatherAdapter.notifyDataSetChanged()
            foreCastAdapter.notifyDataSetChanged()

        }) {
            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            println(it.message)
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonObjectRequest)

    }

    private fun getCityName(longitude: Double, latitude: Double): String {
        var city_name = "Not Found"
        val geocoder = Geocoder(baseContext, Locale.getDefault())
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 10)
            if (addresses != null) {
                for (address in addresses) {
                    val city = address.locality
                    if (city != null && city.isNotBlank()) {
                        city_name = city
                    }
                }
            }

        } catch (ec: IOException) {
            ec.printStackTrace()
        }
        return city_name
    }

    private fun openForeCastInDetail(date:String)
    {
        val intent = Intent(this,ForeCastViewActivity::class.java)
        intent.putExtra("dt",date)
        intent.putExtra("city",city_name)
        startActivity(intent)
    }

    private fun openHourCasrInDetail(date:String)
    {
        val intent = Intent(this,HourInfoActivity::class.java)
        val raw_time= SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
        val structure_time= SimpleDateFormat("HH", Locale.US)
        val structure_date=SimpleDateFormat("yyyy-MM-dd", Locale.US)
        try {
            val t=raw_time.parse(date)
            val str_hour= t?.let { structure_time.format(it).toString() }
            val str_date= t?.let { structure_date.format(it).toString() }

            intent.putExtra("hour",str_hour)
            intent.putExtra("dt",str_date)
            intent.putExtra("city",city_name)
            startActivity(intent)
        }catch (ec: ParseException){
            ec.printStackTrace()
            Toast.makeText(this,ec.toString(),Toast.LENGTH_LONG).show()
            finish()
        }

    }

}