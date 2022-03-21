package com.uce.startup.fragments.client

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.uce.startup.adapters.FavoriteAdapter
import com.uce.startup.models.Product
import com.uce.startup.utils.SharedPref
import com.uce.startup.R

class ClientOrdersFragment : Fragment() {

    val TAG = "FavoriteFragment"
    var myView: View? = null
    var recyclerViewFavorite: RecyclerView? = null

    var toolbar: Toolbar? = null
    var adapter: FavoriteAdapter? = null
    var sharedPref: SharedPref? = null
    var gson = Gson()
    var selectedProducts = ArrayList<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_client_orders, container, false)

        toolbar = myView?.findViewById(R.id.toolbar)
        toolbar?.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        toolbar?.title = "Favoritos"
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        recyclerViewFavorite = myView?.findViewById(R.id.recyclerview_favorites)
        recyclerViewFavorite?.layoutManager = LinearLayoutManager(requireContext())

        sharedPref = SharedPref(requireActivity())

        getProductsFromSharedPref()
        return myView
    }

    private fun getProductsFromSharedPref() {

        if (!sharedPref?.getData("order").isNullOrBlank()) { // EXISTE UNA ORDEN EN SHARED PREF
            val type = object: TypeToken<ArrayList<Product>>() {}.type
            selectedProducts = gson.fromJson(sharedPref?.getData("order"), type)

            adapter = FavoriteAdapter(requireActivity(), selectedProducts)
            recyclerViewFavorite?.adapter = adapter

        }

    }


}