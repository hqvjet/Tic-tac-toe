package com.example.first

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    //This is turn counter of a battle
    var turn = 1

    //3x3 table for handling game data
    var table = Array(3) { Array(3) { 0 } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {
        setContentView(R.layout.game_interface)
    }

    //func that contain game's rule
    private fun handleGame() {

    }

    fun attack(view: View) {
        val item: ImageView = findViewById(view.id)

        //idea: get const of fired image and compare with specified image's const
        val imgID = item.drawable.constantState;
        val imgID2 = getDrawable(R.drawable.none)?.constantState

        //caro square handler
        var check = "No"
        if (imgID == imgID2) {
            check = "Yes"
            if (turn % 2 == 0)
                item.setImageResource(R.drawable.x)
            else
                item.setImageResource(R.drawable.o)
            ++turn
            handleGame()
        }

        //tracker
        val tracker: TextView = findViewById(R.id.textView2)
        tracker.text = check
    }

}