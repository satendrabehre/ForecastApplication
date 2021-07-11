package com.example.forecastapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.forecastapplication.data.db.room.entity.City

class CityListAdapter(context: Context, private val listener: Listener) :
    RecyclerView.Adapter<CityListAdapter.CityViewHolder?>() {
    interface Listener {
        fun onClickDelete(city: City?, position: Int)
        fun onClickItem(city: City?, position: Int)
    }

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mCities: List<City>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView: View = mInflater.inflate(R.layout.recyclerview_item, parent, false)
        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        if (mCities != null) {
            val current: City = mCities!![position]
            holder.tvName.text = current.cityName
            holder.tvDelete.setOnClickListener {
                listener.onClickDelete(current, position)
            }
            holder.tvName.setOnClickListener {
                listener.onClickItem(current, position)
            }
        } else {
            // Covers the case of data not being ready yet.
            holder.tvName.setText("NA")
        }
    }

    override fun getItemCount(): Int {
        return if (mCities != null) mCities!!.size else 0
    }

    fun getItemAtPosition(position: Int): City {
        return mCities!![position]
    }

    fun setData(cities: List<City>?) {
        mCities = cities
        notifyDataSetChanged()
    }

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById<TextView>(R.id.tvName)
        val tvDelete: TextView = itemView.findViewById<TextView>(R.id.tvDelete)

    }



}