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

    private lateinit var tracker: TextView

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
        println("Target: $target")

        var startPointX = keyX
        var startPointY = keyY
        //end game

        fun endgame() {
            //handle
            this.tracker.text = "end"
            println("end")
        }

        fun getStartPoint(y: Int, x: Int) {
            var axisY = keyY - y
            var axisX = keyX - x

            while (axisY >= 0 && axisX >= 0
                && axisX < maxRow && axisY < maxColumn
                && table[axisY][axisX] == target
            ) {
                axisY -= y
                axisX -= x
            }

            startPointX = axisX + x
            startPointY = axisY + y
            println("X: $startPointX")
            println("Y: $startPointY")
        }

        fun checkLine(y: Int, x: Int): Boolean {

            getStartPoint(y, x)

            startPointX += x
            startPointY += y

            var count = 1
            while (count != milestone && startPointX < maxColumn && startPointY < maxRow
                && startPointX >= 0 && startPointY >= 0
                && table[startPointY][startPointX] == target
            ) {

                ++count
                startPointX += x
                startPointY += y
                println("Count: $count")

            }

            if (count == milestone)
                return true
            return false
        }

        println("checking")
        if (checkLine(1, 1) || checkLine(0, 1) || checkLine(1, -1) || checkLine(1, 0)) {
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
        this.tracker = findViewById(R.id.textView2)

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
            handleGame(col - 1, row - 1, if (turn % 2 == 0) 1 else 2)
            ++turn
        }

        for (i in 0..2 step 1) {
            for (j in 0..2 step 1)
                println(table[i][j])
        }


    }

}