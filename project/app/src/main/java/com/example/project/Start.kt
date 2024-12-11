package com.example.project

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bumptech.glide.Glide

class Start : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val image: ImageView = view.findViewById(R.id.imageView)
        val imageUrl: String = "https://static01.nyt.com/images/2022/03/02/crosswords/alpha-wordle-icon-new/alpha-wordle-icon-new-smallSquare252-v3.png?format=pjpg&quality=75&auto=webp&disable=upscale"
        Glide.with(this).load(imageUrl).into(image)

        val button: Button = view.findViewById(R.id.startButton)
        val streakText: TextView = view.findViewById(R.id.streakText)
        val prefs: SharedPreferences = (context as MainActivity).getPreferences(Context.MODE_PRIVATE)
        val winStreak: Int = prefs.getInt("streak", 0) // -1 returned if key not found
        streakText.text = "Win Streak: " + winStreak



        button.setOnClickListener{
            val navController: NavController = view.findNavController()
            navController.navigate(R.id.action_start2_to_game)
        }
    }

}