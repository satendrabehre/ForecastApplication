package com.example.forecastapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.forecastapplication.*
import com.example.forecastapplication.data.db.room.entity.City
import com.example.forecastapplication.data.repositories.CityRepository
import com.example.forecastapplication.data.repositories.WeatherInfoRepository
import com.example.forecastapplication.data.server.ApiClient
import com.example.forecastapplication.data.server.ApiService
import javax.inject.Inject

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private var adapter: CityListAdapter? = null
    private var apiService: ApiService? = null
    private var tvCity: TextView? = null
    private var tvTemp: TextView? = null
    private var tvHumidity: TextView? = null
    private var tvWind: TextView? = null

    @Inject
    lateinit var cityRepository: CityRepository
    @Inject
    lateinit var weatherRepository: WeatherInfoRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (requireContext().applicationContext as MyApplication).appComponent.inject(this)
        homeViewModel = ViewModelProvider(this,
            ViewModelFactory(cityRepository, weatherRepository))
            .get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)

        textView.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_map)
        }

        val navController = findNavController();
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("city")?.observe(
            viewLifecycleOwner
        ) { city ->
            Log.i(Companion.TAG, "result city: $city")
            onNewCity(city)
        }

        //activity?.setActionBar(root.findViewById(R.id.toolbar))
        //root.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        tvCity = root.findViewById(R.id.tv_city)
        tvTemp = root.findViewById(R.id.tv_temp)
        tvHumidity = root.findViewById(R.id.tv_humidity)
        tvWind = root.findViewById(R.id.tv_wind)

        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CityListAdapter(requireContext(), object : CityListAdapter.Listener {
            override fun onClickDelete(city: City?, position: Int) {
                homeViewModel.deleteCity(city)
            }

            override fun onClickItem(city: City?, position: Int) {
                //homeViewModel.deleteCity(city)
                city?.let { homeViewModel.insert(City(city.cityName)) }
                homeViewModel.deleteCity(city)
            }
        })
        recyclerView.adapter = adapter

        homeViewModel.getAllCities()?.observe(viewLifecycleOwner, { list->
            adapter!!.setData(list as List<City>?)
            list?.let {
                if(it.isNotEmpty()) {
                    val cityName = it[0].cityName
                    tvCity?.text = cityName
                    homeViewModel.getCurrentWeather(cityName, Constants.UNITS, Constants.LANGUAGE, Constants.API_KEY)
                }
            }
        })
        homeViewModel.getResultLiveData().observe(viewLifecycleOwner, {
            tvTemp?.text = "Temperature: " + it.getMain().getTemp().toString()
            tvHumidity?.text = "Humidity: " + it.getMain().getHumidity().toString()
            tvWind?.text = "Wind: " + it.getWind().getSpeed().toString()
        })
        homeViewModel.getFailLiveData().observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

        return root
    }

    private fun onNewCity(cityName: String?){
        if(cityName != null){
            val city = City(cityName)
            homeViewModel.insert(city)
        }else {
            Toast.makeText(requireContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
    }

}