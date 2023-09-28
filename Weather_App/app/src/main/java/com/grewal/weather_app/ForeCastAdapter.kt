package com.grewal.weather_app

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grewal.weather_app.databinding.ForecastRecyclerViewBinding

class ForeCastAdapter(
    private var context: Context, private var forecastArrayList: ArrayList<WeatherForeCastModel>
) : RecyclerView.Adapter<ForeCastAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: ForecastRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root)
    {
        init {
            binding.root.setOnClickListener {
                // Handle item click here
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = forecastArrayList[position]
                    onItemClickListener?.invoke(clickedItem)
                }
            }
        }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ForecastRecyclerViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return forecastArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(forecastArrayList[position]) {
                com.squareup.picasso.Picasso.get().load("https:".plus(icon))
                    .into(binding.rvConditionIcon2)
                binding.rvCondition2.text = condition
                binding.rvDate.text = date
            }
        }

    }



    private var onItemClickListener: ((WeatherForeCastModel) -> Unit)? = null

    fun setOnClickListener(listener: (WeatherForeCastModel) -> Unit) {
        onItemClickListener = listener
    }


}

