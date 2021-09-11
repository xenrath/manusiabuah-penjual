package com.xenrath.manusiabuah.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.database.PrefManager
import com.xenrath.manusiabuah.ui.AuthActivity
import com.xenrath.manusiabuah.ui.notification.NotificationFragment
import com.xenrath.manusiabuah.ui.home.HomeFragment
import com.xenrath.manusiabuah.ui.login.LoginActivity
import com.xenrath.manusiabuah.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity(), MainContract.View {

    lateinit var prefManager: PrefManager
    private lateinit var presenter: MainPresenter

    private val fragmentHome: Fragment = HomeFragment()
    private val fragmentNotification: Fragment = NotificationFragment()
    private val fragmentProfile: Fragment = ProfileFragment()
    private val fm: FragmentManager = supportFragmentManager
    private var active: Fragment = fragmentHome

    private lateinit var menu: Menu
    private lateinit var menuItem: MenuItem
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefManager = PrefManager(this)
        presenter = MainPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        if (!prefManager.prefLogin) {
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun initActivity() {
        fm.beginTransaction().add(R.id.nav_host_fragment, fragmentHome).show(fragmentHome).commit()
        fm.beginTransaction().add(R.id.nav_host_fragment, fragmentNotification)
            .hide(fragmentNotification).commit()
        fm.beginTransaction().add(R.id.nav_host_fragment, fragmentProfile).hide(fragmentProfile)
            .commit()

        bottomNavigationView = findViewById(R.id.nav_view)
        menu = bottomNavigationView.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true
    }

    override fun initListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    callFragment(0, fragmentHome)
                }
                R.id.navigation_notifications -> {
                    callFragment(1, fragmentNotification)
                }
                R.id.navigation_profile -> {
                    if (prefManager.prefLogin) {
                        callFragment(2, fragmentProfile)
                    } else {
                        startActivity(Intent(this, AuthActivity::class.java))
                    }
                }
            }
            false
        }
    }

    override fun callFragment(int: Int, fragment: Fragment) {
        menuItem = menu.getItem(int)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }
}