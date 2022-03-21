package com.uce.startup.activities.client.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.uce.startup.fragments.client.ClientCategoriesFragment
import com.uce.startup.fragments.client.ClientOrdersFragment
import com.uce.startup.fragments.client.ClientProfileFragment
import com.uce.startup.models.User
import com.uce.startup.utils.SharedPref
import com.uce.startup.R

class ClientHomeActivity : AppCompatActivity() {

    private val TAG = "ClientHomeActivity"
//    var buttonLogout: Button? = null
    var sharedPref: SharedPref? = null

    var bottomNavigation: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_home)
        sharedPref = SharedPref(this)
//        buttonLogout = findViewById(R.id.btn_logout)
//        buttonLogout?.setOnClickListener { logout() }

        openFragment(ClientCategoriesFragment())

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation?.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.item_home -> {
                    openFragment(ClientCategoriesFragment())
                    true
                }

                R.id.item_orders -> {
                    openFragment(ClientOrdersFragment())
                    true
                }

                R.id.item_profile -> {
                    openFragment(ClientProfileFragment())
                    true
                }

                else -> false

            }

        }

        getUserFromSession()
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }



    private fun getUserFromSession() {

        val gson = Gson()

        if (!sharedPref?.getData("user").isNullOrBlank()) {
            // SI EL USARIO EXISTE EN SESION
            val user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
            Log.d(TAG, "Usuario: $user")
        }

    }
}