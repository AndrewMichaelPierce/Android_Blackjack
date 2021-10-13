package com.example.pierceandrew_courseproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.help.*

class help : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.help)
    }

    fun textDisplayBlackjack(view: View) {
        info.text = "Blackjack is a game where the player tries to obtain a total of cards without " +
                " going over 21 points while at the same time trying to beat the dealers score."
    }

    fun textDisplayAmericanBlackjack(view: View) {
        info.text = "In American Blackjack, both the dealer and the player are dealt two cards at the beginning of a round."
    }

    fun textDisplayEuropeanBlackjack(view: View) {
        info.text = "In European Blackjack, the dealer is dealt one card at the start of a round while the player is dealt two."
    }

    fun textDisplayPreferences(view: View) {
        info.text = "On the preference screen you can change how the back of the card looks, to" +
                " how the cards are shuffled and how many decks of cards are used."
    }

    fun textDisplayAddChips(view: View) {
        info.text = "On the 'Add Chips' screen, you can replenish the amount of chips you play with" +
                " on the next game."
    }
}