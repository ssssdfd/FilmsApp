package com.example.mymovieapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.example.mymovieapp.databinding.ActivityMainBinding
import com.example.mymovieapp.fragments.PopularFilmsFragment
import com.example.mymovieapp.fragments.TopFilmsFragment
import com.example.mymovieapp.fragments.UpcomingFilmsFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val fragmentsList: List<Fragment> = listOf(
        UpcomingFilmsFragment(),
        PopularFilmsFragment(),
        TopFilmsFragment())
    private val fragTitles = listOf("Upcoming","Popular", "Top")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.myFragmentContainer.adapter = ViewPagerAdapter(this, fragmentsList)
        TabLayoutMediator(binding.myTabLayout, binding.myFragmentContainer){tab, pos->
            tab.text = fragTitles[pos]
        }.attach()

         this.onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemFavorite->{
                startActivity(Intent(this, FavoriteFilmsActivity::class.java))
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return true
    }
}