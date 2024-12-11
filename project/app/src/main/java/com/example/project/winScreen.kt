package com.example.project

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import org.w3c.dom.Text

class winScreen : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_win_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.returnButton)
        button.setOnClickListener{
            val navController: NavController = view.findNavController()
            navController.navigate(R.id.action_winScreen_to_start2)
        }

        val streakText: TextView = view.findViewById(R.id.streakTextTwo)
        val prefs: SharedPreferences = (context as MainActivity).getPreferences(Context.MODE_PRIVATE)
        val winStreak: Int = prefs.getInt("streak", 0) // -1 returned if key not found
        streakText.text = "Win Streak: " + winStreak
    }
}