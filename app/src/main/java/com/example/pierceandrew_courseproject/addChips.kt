package com.example.pierceandrew_courseproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.view.View
import kotlinx.android.synthetic.main.add_chips.*
import java.lang.reflect.Array.get
import java.util.ArrayDeque
import java.util.Collections.shuffle
import kotlin.math.floor
import android.widget.SeekBar
import android.R
import android.R.attr
import android.widget.Button
import android.widget.RadioButton
import android.widget.SeekBar.OnSeekBarChangeListener
import android.R.attr.data
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.american_blackjack.*

class addChips : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.pierceandrew_courseproject.R.layout.add_chips)
        var passedInput = intent.getStringExtra("userVariables")
        var passedCard = intent.getStringExtra("cardBackground")
        if (passedInput != null) {
            playerBalance = passedInput.toInt()
            currentBalance.text = passedInput
        }
        if (passedCard != null) {
            passedCardFinal = passedCard
        }
    }

    var userVariables = "0"
    var addAmount = 0
    var playerBalance = 0
    var passedCardFinal = "0"

    fun chipRadioButtonClicked(view: View) {
        addAmount = 0
        if (view is RadioButton) {
            //val buttonChecked = view.isChecked
            if (addingOneChip.isChecked) {
                addAmount += 1
            } else if (addingFiveChips.isChecked) {
                addAmount += 5
            } else if (addingTwentyFiveChips.isChecked) {
                addAmount += 25
            } else if (addingOneHundredChips.isChecked) {
                addAmount += 100
            } else if (addingOneThousandChips.isChecked) {
                addAmount += 1000
            } else if (addingFiveThousandChips.isChecked) {
                addAmount += 5000
            }
        }
        amountToAdd.text = addAmount.toString()
    }

    fun addChipsButtonPushed(view: View) {
        playerBalance += addAmount
        currentBalance.text = playerBalance.toString()
        addAmount = 0
        amountToAdd.text = ""
    }

    override fun onBackPressed() {
        var intent = Intent(this,MainActivity::class.java)
        intent.putExtra("playerChips", playerBalance.toString())
        intent.putExtra("cardBack", passedCardFinal)
        startActivity(intent)
    }
}
