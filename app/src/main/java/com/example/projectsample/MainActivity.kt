package com.example.projectsample

import android.app.AlertDialog
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.window.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.projectsample.Fragments.CategoryFragment
import com.example.projectsample.Fragments.HomeFragment
import com.example.projectsample.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {
    var keepSplashOnScreen = true
    val delay = 4000L
    private lateinit var binding: ActivityMainBinding
    private var homeFragment = HomeFragment()
    private lateinit var connectivityObserver: ConnectivityObserver

    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition{keepSplashOnScreen}
        Handler(Looper.getMainLooper()).postDelayed({keepSplashOnScreen = false},delay)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setMessage("No Internet Connection")
        builder.setTitle("Attention!")
        builder.setCancelable(false)
        val alertDialog = builder.create()
        val x = Snackbar.make(
            binding.frameLayout,
            R.string.no_internet_connection,
            Snackbar.LENGTH_INDEFINITE
        )
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        connectivityObserver.observe().onEach {
            if (it == ConnectivityObserver.Status.Available) {
                alertDialog.dismiss()
                Log.i(ContentValues.TAG, "onCreate: status in $it")
                //Snackbar.make(binding.frameLayout, "the connection is available", Snackbar.LENGTH_SHORT) .show()
            } else if (it == ConnectivityObserver.Status.Lost) {
                Log.i(ContentValues.TAG, "onCreate: status in $it")
                alertDialog.show()
            }

        }.launchIn(lifecycleScope)

        beginTransaction(homeFragment)
        binding.bottomNavigationView.setOnItemReselectedListener {
            when(it.itemId){
                R.id.action_home -> {
                    beginTransaction(homeFragment)
                }
                R.id.action_category -> {
                    beginTransaction(CategoryFragment())
                }
                else -> return@setOnItemReselectedListener
            }
            return@setOnItemReselectedListener
        }
    }

    private fun beginTransaction(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }

}