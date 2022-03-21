package com.uce.startup.activities.undertake.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.uce.startup.activities.MainActivity
import com.uce.startup.fragments.client.ClientProfileFragment
import com.uce.startup.fragments.undertake.UndertakeCategoryFragment
import com.uce.startup.fragments.undertake.UndertakeProductFragment
import com.uce.startup.models.User
import com.uce.startup.utils.SharedPref
import com.uce.startup.R

class UndertakeHomeActivity : AppCompatActivity() {

    private val TAG = "RestaurantHomeActivity"
    //    var buttonLogout: Button? = null
    var sharedPref: SharedPref? = null

    var bottomNavigation: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_home)
        sharedPref = SharedPref(this)

        openFragment(UndertakeCategoryFragment())

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation?.setOnItemSelectedListener {

            when (it.itemId) {


                R.id.item_category -> {
                    openFragment(UndertakeCategoryFragment())
                    true
                }

                R.id.item_product -> {
                    openFragment(UndertakeProductFragment())
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

    private fun logout() {
        sharedPref?.remove("user")
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
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