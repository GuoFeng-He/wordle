package com.example.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.findNavController

class loseScreen : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lose_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.returnButtonTwo)
        val display: TextView = view.findViewById(R.id.loseTextTwo)
        val activity: MainActivity = context as MainActivity
        display.text = "The correct word was: " + activity.getWord()
        button.setOnClickListener{
            val navController: NavController = view.findNavController()
            navController.navigate(R.id.action_loseScreen_to_start2)
        }
    }
}