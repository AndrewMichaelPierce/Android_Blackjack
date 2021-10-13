package com.example.pierceandrew_courseproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.add_chips.*
import kotlinx.android.synthetic.main.preferences.*
import java.lang.reflect.Array.get
import java.util.ArrayDeque
import java.util.Collections.shuffle
import kotlin.math.floor

class preferences : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preferences)
        var passedInput = intent.getStringExtra("userVariables")
        if (passedInput != null) {
            playerBalance = passedInput.toInt()
        }
    }

    override fun onBackPressed() {
        var intent = Intent(this,MainActivity::class.java)
        if (goldStar.isChecked) {
            intent.putExtra("cardBack", "1")
            intent.putExtra("playerChips", playerBalance.toString())
            startActivity(intent)
        } else if (greyStar.isChecked) {
            intent.putExtra("cardBack", "0")
            intent.putExtra("playerChips", playerBalance.toString())
            startActivity(intent)
        } else {
            intent.putExtra("cardBack", "2")
            intent.putExtra("playerChips", playerBalance.toString())
            startActivity(intent)
        }
    }
    var userVariables = "0"
    var addAmount = 0
    var playerBalance = 0


}