package com.lime.android.screens.dashboard

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.lime.android.R
import com.lime.android.fragments.aboutus.AboutUsFragment
import com.lime.android.fragments.home.HomeFragment
import com.lime.android.fragments.orderconfirmation.OrderConfirmationFragment
import com.lime.android.fragments.orderhistory.OrderHistoryFragment
import com.lime.android.fragments.termsandconditions.TnCFragment
import com.lime.android.screens.login.LoginActivity
import com.lime.android.service.LIME_IMAGE_URL
import com.lime.android.sharedrepository.LimeSharedRepositoryImpl
import com.lime.android.ui.BaseFragment
import com.lime.android.util.GLOBAL_TAG
import com.lime.android.util.LimeUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProviders.of(this, LimeUtils.viewModelFactory { MainViewModel(this) }).get(MainViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Toolbar>(R.id.toolbar).visibility = View.VISIBLE
        configureDrawerMenu()
        notificationChannelCreation()
        viewModel.updateFcmToken(LimeSharedRepositoryImpl(this).fcmToken)
    }

    private fun notificationChannelCreation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.app_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(
                NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW)
            )
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        intent.extras?.let {
            for (key in it.keySet()) {
                val value = intent.extras?.get(key)
                Log.d(GLOBAL_TAG, "Key: $key Value: $value")
            }
        }
        // [END handle_data_extras]
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

            LimeUtils.setImageUsingPicassoWithPlaceHolder(
                this@MainActivity,
                findViewById<ImageView>(R.id.img_user_image),
                LIME_IMAGE_URL.plus(LimeSharedRepositoryImpl(this@MainActivity).loggedInUser.profile_pic),
                R.drawable.ic_user_pic_icon)
            findViewById<TextView>(R.id.tv_user_name).text = LimeSharedRepositoryImpl(this@MainActivity).loggedInUser.user_name
            findViewById<ImageView>(R.id.img_back).setOnClickListener {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START)
                }else{
                    drawer.openDrawer(GravityCompat.START)
                }
            }
        }
    }

    private fun navigateToMenuFragment(position: Int) {
        when(position){
            0 -> if (checkCurrentFragment() !is HomeFragment) openFragment(R.id.mainFragment)
            2 -> if (checkCurrentFragment() !is OrderHistoryFragment) openFragment(R.id.orderHistoryFragment)
            3 -> if (checkCurrentFragment() !is AboutUsFragment) openFragment(R.id.aboutUsFragment)
            4 -> if (checkCurrentFragment() !is TnCFragment) openFragment(R.id.tnCFragment)
            5 -> logout()
            else -> checkCurrentFragment()
        }
    }

    private fun logout() {
        LimeSharedRepositoryImpl(this).cleanup()
        startActivity(Intent(this,LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
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

    fun setBackButtonVisibility(visible: Int){
        findViewById<ImageView>(R.id.img_back).apply {
            visibility = visible
        }
    }

    fun setToolbarVisibiliy(visibility: Int){
        findViewById<Toolbar>(R.id.toolbar).visibility = visibility
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val childFragments = navHostFragment?.childFragmentManager?.fragments
        childFragments?.forEach { it.onActivityResult(requestCode, resultCode, data) }
    }

    override fun onBackPressed() {
        val fragcount: Int = supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.backStackEntryCount ?: -1
        var fragment: Fragment? = null
        if (supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.fragments.isNullOrEmpty()){
            fragment= supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.fragments?.get(fragcount)
        }
        if(fragment != null && fragment is OrderConfirmationFragment){
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            navHostFragment?.findNavController()?.navigate(R.id.action_orderConfirmationFragment_to_mainFragment)
        }
        else if (fragcount == 0)
            showCancelSnackBar()
        else
            super.onBackPressed()
    }

    private fun showCancelSnackBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val snack = Snackbar.make(toolbar,"Do you want to close the app?",Snackbar.LENGTH_LONG)
        snack.setAction("Yes", View.OnClickListener {
            finish()
        })
        snack.setActionTextColor(ContextCompat.getColor(this,R.color.white))
        snack.show()
    }
}
