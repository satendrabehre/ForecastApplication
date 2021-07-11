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
import com.example.forecastapplication.R
import com.example.forecastapplication.CityListAdapter
import com.example.forecastapplication.data.db.room.entity.City

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private var adapter: CityListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.getAllCities()?.observe(viewLifecycleOwner, Observer {
            Log.i(Companion.TAG, "All Cities: ${it.toString()}")
        })

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

        //requireContext().setSupportActionBar(root.findViewById(R.id.toolbar))
        //root.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title

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

        homeViewModel.getAllCities()?.observe(viewLifecycleOwner, { it ->
                adapter!!.setData(it as List<City>?)
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