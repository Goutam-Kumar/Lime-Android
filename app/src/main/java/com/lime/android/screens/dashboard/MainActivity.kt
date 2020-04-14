package com.lime.android.screens.dashboard

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.lime.android.R
import com.lime.android.fragments.aboutus.AboutUsFragment
import com.lime.android.fragments.home.HomeFragment
import com.lime.android.fragments.termsandconditions.TnCFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Toolbar>(R.id.toolbar).visibility = View.VISIBLE
        configureDrawerMenu()
    }

    private fun configureDrawerMenu() {
        val drawer = findViewById<DrawerLayout>(R.id.nav_drawer)
        findViewById<NavigationView>(R.id.nav_view).apply {
            findViewById<RecyclerView>(R.id.rcv_drawer_menu).apply {
                layoutManager = LinearLayoutManager(this@MainActivity,RecyclerView.VERTICAL,false)
                addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
                adapter = DrawerAdapter(
                    {position ->
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START)
                        }else{
                            drawer.openDrawer(GravityCompat.START)
                        }
                        navigateToMenuFragment(position)
                    },
                    this@MainActivity
                )
            }
        }
    }

    private fun navigateToMenuFragment(position: Int) {
        when(position){
            0 -> if (checkCurrentFragment() !is HomeFragment) openFragment(R.id.mainFragment)
            3 -> if (checkCurrentFragment() !is AboutUsFragment) openFragment(R.id.aboutUsFragment)
            4 -> if (checkCurrentFragment() !is TnCFragment) openFragment(R.id.tnCFragment)
            5 -> logout()
            else -> checkCurrentFragment()
        }
    }

    private fun logout() {
        //TODO clear the login sharedPref
    }

    private fun checkCurrentFragment(): Fragment?{
        val navHostFragment = supportFragmentManager.primaryNavigationFragment
        return navHostFragment?.childFragmentManager?.fragments?.get(0)
    }

    private fun openFragment(fragmentId: Int){
        val navHostFragment = nav_host_fragment as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.main_navigation)
        val navController = navHostFragment.navController
        navGraph.startDestination = fragmentId
        navController.graph = navGraph
    }

    fun setTitle(title: String){
        findViewById<TextView>(R.id.txt_title).text = title
    }

    fun setMenuVisibility(visible: Int){
        val drawer = findViewById<DrawerLayout>(R.id.nav_drawer)
        findViewById<ImageView>(R.id.img_menu).apply {
            visibility = visible
            setOnClickListener {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START)
                }else{
                    drawer.openDrawer(GravityCompat.START)
                }
            }
        }
        if(visible == View.GONE){
            findViewById<NavigationView>(R.id.nav_view).visibility = View.GONE
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }else{
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    fun setBackButtonVisibility(visibility: Int){
        findViewById<ImageView>(R.id.img_back).visibility = visibility
    }

    fun setToolbarVisibiliy(visibility: Int){
        findViewById<Toolbar>(R.id.toolbar).visibility = visibility
    }
}
