package com.example.project

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.coroutines.newSingleThreadContext
import java.io.BufferedReader
import java.io.InputStreamReader

class game : Fragment() {
    lateinit var button: Button
    lateinit var input: EditText
    lateinit var error: TextView
    lateinit var reload: ImageButton
    lateinit var layout: ArrayList<TextView>
    lateinit var wordList: MutableList<String>
    lateinit var allowedGuesses: MutableList<String>
    lateinit var guesses: ArrayList<String>
    lateinit var word: String
    var winStreak: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController: NavController = view.findNavController()

        button = view.findViewById(R.id.guessButton)
        input = view.findViewById(R.id.input)
        error = view.findViewById(R.id.errorMessage)
        reload = view.findViewById(R.id.refresh)
        layout = ArrayList()
        wordList = readFile("words.txt")
        allowedGuesses = readFile("allowed_guesses.txt")
        guesses = ArrayList()
        word = getWord(wordList)
        val activity: MainActivity = context as MainActivity
        winStreak = activity.getStreak()

        layout.add(view.findViewById(R.id.box1))
        layout.add(view.findViewById(R.id.box2))
        layout.add(view.findViewById(R.id.box3))
        layout.add(view.findViewById(R.id.box4))
        layout.add(view.findViewById(R.id.box5))
        layout.add(view.findViewById(R.id.box6))
        layout.add(view.findViewById(R.id.box7))
        layout.add(view.findViewById(R.id.box8))
        layout.add(view.findViewById(R.id.box9))
        layout.add(view.findViewById(R.id.box10))
        layout.add(view.findViewById(R.id.box11))
        layout.add(view.findViewById(R.id.box12))
        layout.add(view.findViewById(R.id.box13))
        layout.add(view.findViewById(R.id.box14))
        layout.add(view.findViewById(R.id.box15))
        layout.add(view.findViewById(R.id.box16))
        layout.add(view.findViewById(R.id.box17))
        layout.add(view.findViewById(R.id.box18))
        layout.add(view.findViewById(R.id.box19))
        layout.add(view.findViewById(R.id.box20))
        layout.add(view.findViewById(R.id.box21))
        layout.add(view.findViewById(R.id.box22))
        layout.add(view.findViewById(R.id.box23))
        layout.add(view.findViewById(R.id.box24))
        layout.add(view.findViewById(R.id.box25))
        layout.add(view.findViewById(R.id.box26))
        layout.add(view.findViewById(R.id.box27))
        layout.add(view.findViewById(R.id.box28))
        layout.add(view.findViewById(R.id.box29))
        layout.add(view.findViewById(R.id.box30))

        if (savedInstanceState != null){
            guesses = savedInstanceState.getStringArrayList("guesses") ?: ArrayList()
            word = savedInstanceState.getString("word").toString()
            if (guesses.isNotEmpty()){
                for (guess in guesses){
                    initiateChange(compareWord(word, guess), guess)
                }
            }
        }

        if (activity.getWord().isNotEmpty()) {
            word = activity.getWord()
        }
        if (activity.getGuesses().isNotEmpty()) {
            guesses = activity.getGuesses()
            for (guess in guesses){
                initiateChange(compareWord(word, guess), guess)
            }
        }
        if (guesses.size >= 6 || guesses.contains(word)){
            reset()
        }

        activity.setWord(word)

        reload.setOnClickListener {
            reset()
            winStreak = 0
            applyPref()
        }

        button.setOnClickListener {
            Log.i("answer", word)
            val inputText = input.text.toString().lowercase()
            if (inputText.length == 5 && !guesses.contains(inputText) && (wordList.contains(inputText) || allowedGuesses.contains(inputText))) {
                error.visibility = View.INVISIBLE
                val colors: String = compareWord(word, inputText)
                initiateChange(colors, inputText)
                guesses.add(inputText)
            } else {
                error.visibility = View.VISIBLE
            }
            input.setText("")

            activity.setGuesses(guesses)

            if (guesses.size == 6 && inputText != word){
                winStreak = 0
                applyPref()
                navController.navigate(R.id.action_game_to_loseScreen)
            }
            if (inputText == word){
                Log.i("answer", "Win Streak $winStreak")
                winStreak++
                Log.i("answer", "Win Streak $winStreak")
                applyPref()
                navController.navigate(R.id.action_game_to_winScreen)
                reset()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("guesses", guesses)
        outState.putString("word", word)
    }

    override fun onStop() {
        super.onStop()
        applyPref()
    }

    private fun applyPref(){
        val prefs: SharedPreferences = (context as MainActivity).getPreferences(Context.MODE_PRIVATE)
        val prefEditor: SharedPreferences.Editor = prefs.edit()
        prefEditor.putInt("streak", winStreak)
        prefEditor.apply()

        val activity: MainActivity = context as MainActivity
        activity.setStreak(winStreak)
    }


    private fun readFile(fileName: String): MutableList<String> {
        val reader: BufferedReader = BufferedReader(InputStreamReader((context as MainActivity).assets.open(fileName)))
        val lineList = mutableListOf<String>()
        reader.forEachLine { lineList.add(it) }
        return lineList
    }

    private fun getWord(list: MutableList<String>): String{
        val rand = Math.round(Math.random() * list.size).toInt()
        return list[rand]
    }

    // parameters: b = grey, y = yellow, g = green
    private fun compareWord(correct: String, guess: String): String{
        var parameter = ""
        for (i in 0..4){
            if (guess.substring(i, i + 1) == correct.substring(i, i + 1)){
                parameter += "g"
            } else if (correct.indexOf(guess.substring(i, i + 1)) != -1){
                parameter += "y"
            } else {
                parameter += "b"
            }
        }
        return parameter
    }

    private fun initiateChange(colors: String, input: String){
        if (guesses.size >= 0 && layout[0].text == ""){
            changeBoxes(colors, input, 0, 4)
        }
        else if (guesses.size >= 1 && layout[5].text == ""){
            changeBoxes(colors, input, 5, 9)
        }
        else if (guesses.size >= 2 && layout[10].text == ""){
            changeBoxes(colors, input, 10, 14)
        }
        else if (guesses.size >= 3 && layout[15].text == ""){
            changeBoxes(colors, input, 15, 19)
        }
        else if (guesses.size >= 4 && layout[20].text == ""){
            changeBoxes(colors, input, 20, 24)
        }
        else if (guesses.size >= 5 && layout[25].text == ""){
            changeBoxes(colors, input, 25, 29)
        }
    }

    private fun changeBoxes(colors: String, input: String, start: Int, end: Int){
        for (i in start..end){
            val s: Int = i % 5
            if (colors.substring(s, s + 1) == "b") {
                layout[i].setBackgroundColor(Color.GRAY)
            } else if (colors.substring(s, s + 1) == "y") {
                layout[i].setBackgroundColor(Color.parseColor("#c8b653"))
            } else {
                layout[i].setBackgroundColor(Color.parseColor("#6ca965"))
            }

            layout[i].text = input.substring(s, s + 1).uppercase()
        }
    }

    private fun reset(){
        guesses.clear()
        for (view in layout){
            view.text = ""
            view.setBackgroundResource(R.drawable.border)
        }
        word = getWord(wordList)
        val activity: MainActivity = context as MainActivity
        activity.setWord("")
        activity.setGuesses(ArrayList())
    }

}