package com.grewal.weather_app

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.grewal.weather_app.databinding.ActivityHourInfoBinding
import com.squareup.picasso.Picasso
import org.json.JSONException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class HourInfoActivity : AppCompatActivity() {
    lateinit var binding : ActivityHourInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHourInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val hour = intent.getStringExtra("hour")
        val date = intent.getStringExtra("dt")
        val city = intent.getStringExtra("city")

        binding.textCity.text=city
        binding.textDate.text=date

        getWeatherInfo(date.toString(), hour.toString(), city.toString())

    }

    private fun getWeatherInfo(date: String, hour: String, cityName: String) {
        val applicationInfo = applicationContext.packageManager.getApplicationInfo(
            applicationContext.packageName,
            PackageManager.GET_META_DATA
        )

        val api_key =applicationInfo.metaData["keyValue"].toString()
        val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
                "$api_key&q=$cityName&aqi=no&dt=$date&hour=$hour"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->

            try {
                Picasso.get()
                    .load("https://w0.peakpx.com/wallpaper/80/967/HD-wallpaper-beauty-nature-beauty-of-nature-fire-forest-moon-night-stars.jpg")
                    .into(binding.BackGroundImage)

                val forecast = response.getJSONObject("forecast")
                val forecastday = forecast.getJSONArray("forecastday")
                val hourObj = forecastday.getJSONObject(0).getJSONArray("hour").getJSONObject(0)
                val time_1 = hourObj.getString("time")
                val raw_time= SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
                val structure_time= SimpleDateFormat("hh:mm a", Locale.US)
                try {

                    val t=raw_time.parse(time_1)
                    binding.textTime.text= t?.let { structure_time.format(it).toString() }
                }catch (ec: ParseException){
                    ec.printStackTrace()
                    Toast.makeText(this,ec.toString(),Toast.LENGTH_LONG).show()
                }
                val icon = hourObj.getJSONObject("condition")
                    .getString("icon")
                Picasso.get().load("https:".plus(icon)).into(binding.weatherIcon)

                val condition = hourObj.getJSONObject("condition")
                    .getString("text")
                binding.weatherCondition.text=condition

                val temperature =hourObj.getString("temp_c")
                binding.TEMPERATURE.text=
                    resources.getString(R.string.temp_in_celcius_1,temperature)

                val windSpeed = hourObj.getString("wind_kph")
                binding.windSpeed.text = resources
                    .getString(R.string.wind_speed_in_km_1, windSpeed)

                val visbility = hourObj
                    .getString("vis_km")
                binding.visibility.text=
                    resources.getString(R.string.visibility_km,visbility)

                val wind_dir =hourObj.getString("wind_dir")
                binding.windDir.text=
                    resources.getString(R.string.wind_direction,wind_dir)

                val humidity = hourObj.getString("humidity")
                binding.humidity.text = resources
                    .getString(R.string.humidity, humidity)

                val uv = hourObj.getString("uv")
                binding.uv.text =
                    resources.getString(R.string.uv_value,uv)

                val percipitation = hourObj.getString("precip_mm")
                binding.precipitation.text =
                    resources.getString(R.string.precip_in_mm,percipitation)

                val pressure = hourObj.getString("pressure_mb")
                binding.pressure.text=
                    resources.getString(R.string.pressure,pressure)

                val feel_like =hourObj.getString("feelslike_c")
                binding.feelLike.text=
                    resources.getString(R.string.real_feel,feel_like)

                val cloudy = hourObj.getString("cloud")
                binding.cloud.text=
                    resources.getString(R.string.cloud,cloudy)


            } catch (e: JSONException) {
                e.printStackTrace()
                Toast.makeText(this, e.message.plus("/n").plus(e.cause), Toast.LENGTH_LONG).show()

            }

        }) {
            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            println(it.message)
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonObjectRequest)

    }
}