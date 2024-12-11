package com.example.project

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    lateinit var appBarConfig: AppBarConfiguration
    private var word: String = ""
    private var guesses: ArrayList<String> = ArrayList()
    private var winStreak: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val appBar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(appBar)
        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        val navGraph: NavGraph = navController.graph
        appBarConfig = AppBarConfiguration(navGraph)
        setupActionBarWithNavController(navController, appBarConfig)
        val prefs: SharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val num: Int = prefs.getInt("streak", 0) // -1 returned if key not found

        winStreak = num
    }

    override fun onSupportNavigateUp(): Boolean {
        val success: Boolean = navController.navigateUp(appBarConfig)
        return success || super.onSupportNavigateUp()
    }

    fun getWord(): String {
        return word
    }

    fun setWord(newWord: String) {
        word = newWord
    }

    fun getGuesses(): ArrayList<String>{
        return guesses
    }

    fun setGuesses(newGuesses: ArrayList<String>){
        guesses = newGuesses
    }

    fun getStreak(): Int{
        return winStreak
    }

    fun setStreak(newStreak: Int){
        winStreak = newStreak
    }
}