package com.grewal.weather_app

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.grewal.weather_app.databinding.ActivityForeCastViewBinding
import com.squareup.picasso.Picasso
import org.json.JSONException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class ForeCastViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityForeCastViewBinding
    private lateinit var weatherModel: ArrayList<WeatherModel>
    private lateinit var weatherAdapter: WeatherAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForeCastViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        weatherModel = arrayListOf()
        weatherAdapter = WeatherAdapter(this, weatherModel)

        val date = intent.getStringExtra("dt")
        val city = intent.getStringExtra("city")
        binding.textDate.text = date
        binding.textCity.text = city
        binding.hourRv.adapter = weatherAdapter
        getWeatherInfo(date.toString(), city.toString())
        weatherAdapter.setOnClickListener {
                model-> openHourCasrInDetail(model.time,city.toString())
        }

    }

    private fun openHourCasrInDetail(date: String,city_name: String) {
        val intent = Intent(this,HourInfoActivity::class.java)
        val raw_time= SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
        val structure_time= SimpleDateFormat("HH", Locale.US)
        val structure_date= SimpleDateFormat("yyyy-MM-dd", Locale.US)
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

    private fun getWeatherInfo(date: String, cityName: String) {
        val applicationInfo = applicationContext.packageManager.getApplicationInfo(
            applicationContext.packageName,
            PackageManager.GET_META_DATA
        )

        val api_key =applicationInfo.metaData["keyValue"].toString()
        val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
                "$api_key&q=$cityName&aqi=no&dt=$date"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            weatherModel.clear()
            try {
                Picasso.get()
                    .load("https://w0.peakpx.com/wallpaper/80/967/HD-wallpaper-beauty-nature-beauty-of-nature-fire-forest-moon-night-stars.jpg")
                    .into(binding.BackGroundImage)

                val forecast = response.getJSONObject("forecast")
                val forecastday = forecast.getJSONArray("forecastday")
                val day = forecastday.getJSONObject(0).getJSONObject("day")

                val Maxtemperature = day.getString("maxtemp_c")
                binding.MAXTEMP.text = resources
                    .getString(R.string.max_temp_in_celcius, Maxtemperature)

                val Mintemperature = day.getString("mintemp_c")
                binding.MINTEMP.text = resources
                    .getString(R.string.min_temp_in_celcius, Mintemperature)

                val avg_humidity = day.getString("avghumidity")
                binding.avgHumidity.text =
                    resources.getString(R.string.humidity, avg_humidity)

                val total_precipitation = day.getString("totalprecip_mm")
                binding.totalPrecipitation.text =
                    resources.getString(R.string.precip_in_mm, total_precipitation)

                val avg_visibility = day.getString("avgvis_km")
                binding.avgVisibility.text =
                    resources.getString(R.string.avg_visibility_km, avg_visibility)

                val condition = day.getJSONObject("condition")
                    .getString("text")
                binding.weatherCondition.text = condition

                val icon = day.getJSONObject("condition")
                    .getString("icon")


                Picasso.get().load("https:".plus(icon)).into(binding.weatherIcon)

                val MaxwindSpeed = day.getString("maxwind_kph")
                binding.windSpeed.text = resources
                    .getString(R.string.wind_speed_in_km, MaxwindSpeed)

                val astro = forecastday.getJSONObject(0).getJSONObject("astro")

                val sunrise = astro.getString("sunrise")
                binding.sunrise.text=
                    resources.getString(R.string.sunrise,sunrise)

                val sunset = astro.getString("sunset")
                binding.sunset.text=
                    resources.getString(R.string.sunset,sunset)

                val moonrise = astro.getString("moonrise")
                binding.moonrise.text=
                    resources.getString(R.string.moonrise,moonrise)

                val moonset = astro.getString("moonset")
                binding.moonset.text=
                    resources.getString(R.string.moonrise,moonset)

                val hourArray = forecastday.getJSONObject(0).getJSONArray("hour")
                for (i in 0 until hourArray.length()) {
                    val hourObj = hourArray.getJSONObject(i)
                    val time = hourObj.getString("time")
                    val temperature = hourObj.getString("temp_c")
                    val windSpeed = hourObj.getString("wind_kph")
                    val icon = hourObj.getJSONObject("condition").getString("icon")
                    weatherModel.add(WeatherModel(time, temperature, icon, windSpeed))
                }

            } catch (e: JSONException) {
                e.printStackTrace()
                Log.println(
                    Log.ERROR, "jsn", e.message.plus(
                        "------\nCAUSE-${e.cause}\n" +
                                "CAUSE-${e.localizedMessage}\n ${e.toString()} "
                    )
                )

                Toast.makeText(this, e.message.plus("/n").plus(e.cause), Toast.LENGTH_LONG).show()

            }
            weatherAdapter.notifyDataSetChanged()

        }) {
            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            println(it.message)
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonObjectRequest)

    }
}