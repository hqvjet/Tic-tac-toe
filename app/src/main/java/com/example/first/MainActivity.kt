package com.example.first

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.ceil
import kotlin.math.ceil
import kotlin.math.floor

class MainActivity : AppCompatActivity() {

    //This is turn counter of a battle
    var turn = 1

    //3x3 table for handling game data
    var table = Array(3) { Array(3) { 0 } } // X: 1, O: 2

    //Game Resource
    private var maxRow = 3
    private var maxColumn = 3
    private val milestone = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {
        setContentView(R.layout.game_interface)
    }

    //func that contain game's rule
    // X: 1, O: 2
    private fun handleGame(keyY: Int, keyX: Int, target: Int) {

        var startPointX = 0
        var startPointY = 0
        //end game

        fun endgame() {
            //handle
        }

        fun getStartPoint(y: Int, x: Int) {
            var axisY = keyY
            var axisX = keyX

            while (axisY > 0 && axisX > 0
                && axisX < maxRow - 1 && axisY < maxColumn - 1 && table[axisY - y][axisX - x] != 0
                && table[axisY - y][axisX - x] != target
            ) {
                axisY -= y
                axisX -= x
            }

            startPointX = axisX
            startPointY = axisY
        }

        fun checkLine(y: Int, x: Int): Boolean {

            getStartPoint(y, x)

            var count = 1
            while (count != milestone && startPointX < maxColumn - 1 && startPointY < maxRow - 1
                && startPointX > 0 && startPointY > 0
                && table[startPointY + y][startPointX + x] == table[startPointY][startPointX]
            ) {

                ++count
                startPointX += x
                startPointY += y

            }

            if (count == milestone)
                return true
            return false
        }

        if (checkLine(1, 1) || checkLine(0, 1) || checkLine(-1, -1) || checkLine(1, 0)) {
            endgame()
        }
    }

    private fun getIndex(input: String): Int {
        return input.toInt()
    }

    fun attack(view: View) {
        val item: ImageView = findViewById(view.id)

        //idea: get const of fired image and compare with specified image's const
        val imgID = item.drawable.constantState;
        val imgID2 = getDrawable(R.drawable.none)?.constantState

        var col = ceil((getIndex(view.tag.toString()) / maxRow.toDouble())).toInt()
//        var row = getIndex(view.tag.toString()) - 1
//        if(col != 0)
        var row = (getIndex(view.tag.toString()) - 3 * (col - 1))

        //tracker
        val tracker: TextView = findViewById(R.id.textView2)


        //caro square handler
        var check = "No"
        if (imgID == imgID2) {
            check = "Yes"
            if (turn % 2 == 0) {
                item.setImageResource(R.drawable.x)
                table[col - 1][row - 1] = 1
            } else {
                item.setImageResource(R.drawable.o)
                table[col - 1][row - 1] = 2
            }
            ++turn
            handleGame(col, row, if (turn % 2 == 0) 1 else 2)
        }

        for (i in 0..2 step 1) {
            for (j in 0..2 step 1)
                println(table[i][j])
        }


    }

}