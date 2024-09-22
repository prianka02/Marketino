package com.ecommapp.marketino.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ecommapp.marketino.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]


        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.setOnApplyWindowInsetsListener(null)
        bottomNav.setPadding(0, 0, 0, 0)


        val homeFragment: Fragment = HomeFragment()
        val shopFragment: Fragment = ShopFragment()
        val profileFragment: Fragment = ProfileFragment()

        supportFragmentManager.beginTransaction().apply {
            add(R.id.content, homeFragment)
            add(R.id.content, shopFragment)
            add(R.id.content, profileFragment)
            hide(shopFragment)
            hide(profileFragment)
            commit()
        }
        replaceFragment(HomeFragment())

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.shop -> {
                    replaceFragment(ShopFragment())
                    true
                }

                R.id.profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }

                else -> return@setOnItemSelectedListener false
            }


            true
        }


    }

    private fun replaceFragment(homeFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.content, homeFragment)
        fragmentTransaction.commit()

    }

}