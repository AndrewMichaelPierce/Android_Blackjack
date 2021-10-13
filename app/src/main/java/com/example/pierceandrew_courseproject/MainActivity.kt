package com.example.pierceandrew_courseproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var userVariables = intent.getStringExtra("playerChips")
        var cardBackground = intent.getStringExtra("cardBack")
        displayVariables.text = cardBackground
        if (userVariables != null) {
            playerData = userVariables
        }
        if (cardBackground != null) {
            playerBack = cardBackground
        }
    }

    var playerData = "0"
    var playerBack = "0"

    fun launchAmericanBlackjack(view: View) {
        var intent = Intent(this,americanBlackjack::class.java)
        intent.putExtra("userVariables", playerData)
        intent.putExtra("cardBackground", playerBack)
        startActivity(intent)
    }

    fun launchEuropeanBlackjack(view: View) {
       var intent1 = Intent(this,europeanBlackjack::class.java)
        intent1.putExtra("userVariables", playerData)
        intent1.putExtra("cardBackground", playerBack)
        startActivity(intent1)
    }

    fun launchPreferences(view: View) {
        var intent2 = Intent(this,preferences::class.java)
        intent2.putExtra("userVariables", playerData)
        intent2.putExtra("cardBackground", playerBack)
        startActivity(intent2)
    }

    fun launchAddChips(view: View) {
        var intent3 = Intent(this,addChips::class.java)
        intent3.putExtra("userVariables", playerData)
        intent3.putExtra("cardBackground", playerBack)
        startActivity(intent3)
    }

    fun launchHelp(view: View) {
        var intent4 = Intent(this,help::class.java)
        startActivity(intent4)
    }
}