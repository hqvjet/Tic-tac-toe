package com.example.first

import android.os.Bundle
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var alert: TextView
    private lateinit var backdrop: Backdrop
    private lateinit var gridLayout: GridLayout

    //This is turn counter of a battle
    var turn = 1

    //Game Resource
    private var size = 50
    private val milestone = 5

    //3x3 table for handling game data
    private var table = Array(size) { Array(size) { 0 } } // X: 1, O: 2

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(androidx.appcompat.R.style.AlertDialog_AppCompat)
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {
        setContentView(R.layout.game_interface)
        gridLayout = findViewById(R.id.gridlayout)
        gridLayout.columnCount = size
        gridLayout.rowCount = size

        for (i in 1..size * size) {
            val imageView = ImageView(this)
            imageView.layoutParams = ViewGroup.LayoutParams(100, 100)
            imageView.setImageResource(R.drawable.none)
            imageView.setPadding(1, 1, 1, 1)
            imageView.setOnClickListener {
                attack(it)
            }
            imageView.id = i
            imageView.tag = i.toString()
            gridLayout.addView(imageView)
        }

    }

    //func that contain game's rule
// X: 1, O: 2
    private fun handleGame(keyY: Int, keyX: Int, target: Int) {
        println("Target: $target")

        var startPointX = keyX
        var startPointY = keyY
        //end game

        fun endgame(winner: Int) {
            //handle
            backdrop =
                Backdrop(if (winner == 1) "X Won" else "O Won").apply { isCancelable = false }
            backdrop.show(supportFragmentManager, "MyDialogFragment")
        }

        fun getStartPoint(y: Int, x: Int) {
            var axisY = keyY - y
            var axisX = keyX - x

            while (axisY >= 0 && axisX >= 0
                && axisX < size && axisY < size
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
            while (count != milestone && startPointX < size && startPointY < size
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
            endgame(target)
        }
    }

    private fun getIndex(input: String): Int {
        return input.toInt()
    }

    private fun attack(view: View) {
        val item: ImageView = findViewById(view.id)

        //idea: get const of fired image and compare with specified image's const
        val imgID = item.drawable.constantState;
        val imgID2 = getDrawable(R.drawable.none)?.constantState

        val col = ceil((getIndex(view.tag.toString()) / size.toDouble())).toInt()
//        var row = getIndex(view.tag.toString()) - 1
//        if(col != 0)
        val row = (getIndex(view.tag.toString()) - size * (col - 1))

        println(col)
        println(row)

        //tracker
//        this.tracker = findViewById(R.id.textView2)

        //caro square handler
        if (imgID == imgID2) {
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

    fun replay(view: View) {

        gridLayout.removeAllViews()
        table = Array(size) { Array(size) { 0 } }
        turn = 1

        for (i in 1..size * size) {
            val imageView = ImageView(this)
            imageView.layoutParams = ViewGroup.LayoutParams(100, 100)
            imageView.setImageResource(R.drawable.none)
            imageView.setPadding(1, 1, 1, 1)
            imageView.setOnClickListener {
                attack(it)
            }
            imageView.id = i
            imageView.tag = i.toString()
            gridLayout.addView(imageView)
        }

        backdrop.dismiss()
    }

}
