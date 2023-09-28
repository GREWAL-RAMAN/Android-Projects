package com.grewal.weather_app

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.grewal.weather_app.databinding.WeatherRecyclerviewItemBinding
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherAdapter(
    private var context: Context,
    private var WeatherModelArrayList: ArrayList<WeatherModel>
) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: WeatherRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root)
    {
        init {
            binding.root.setOnClickListener {
                // Handle item click here
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = WeatherModelArrayList[position]
                    onItemClickListener?.invoke(clickedItem)
                }
            }
        }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WeatherRecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
    return WeatherModelArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     with(holder){
         with(WeatherModelArrayList[position]){
             Picasso.get().load("https:".plus(icon)).into(binding.rvConditionIcon)
             binding.rvTemperature.text=context.getString(R.string.temp_in_celcius,temperature)
             binding.rvWindSpeed.text=context.getString(R.string.wind_speed_in_km,windSpeed)
             val raw_time=SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.US)
             val structure_time=SimpleDateFormat("hh:mm a", Locale.US)
             try {
                 val t=raw_time.parse(time)
                 binding.rvTime.text= t?.let { structure_time.format(it).toString() }
             }catch (ec:ParseException){
                 ec.printStackTrace()
                 Toast.makeText(context,ec.toString(),Toast.LENGTH_LONG).show()
             }
         }
     }
    }
    private var onItemClickListener: ((WeatherModel) -> Unit)? = null

    fun setOnClickListener(listener: (WeatherModel) -> Unit) {
        onItemClickListener = listener
    }
}