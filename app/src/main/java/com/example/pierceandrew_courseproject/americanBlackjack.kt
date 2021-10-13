package com.example.pierceandrew_courseproject

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import kotlinx.android.synthetic.main.american_blackjack.*
import java.util.ArrayDeque
import kotlin.math.floor
import kotlin.math.round

// Starts page of application
class americanBlackjack : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.american_blackjack)  // Making page look like american_blackjack.xml
        var passedInput = intent.getStringExtra("userVariables")
        if (passedInput != null) {
            playerChipTotal = passedInput.toInt()
        }
        chipTotalDisplay.text = playerChipTotal.toString()
        makeCardsInvisible()
        var passedBackground = intent.getStringExtra("cardBackground")
        if (passedBackground != null) {
            dealerBackground = passedBackground.toInt()
        }
        if (dealerBackground == 0) {
            cardBG.setBackgroundResource(R.drawable.greystar)
        } else if (dealerBackground == 1) {
            cardBG.setBackgroundResource(R.drawable.goldstar)
        } else {
            cardBG.setBackgroundResource(R.drawable.arrow)
        }
    }

    fun makeCardsInvisible() {
        dealerOne.visibility = View.INVISIBLE
        dealerTwo.visibility = View.INVISIBLE
        dealerThree.visibility = View.INVISIBLE
        dealerFour.visibility = View.INVISIBLE
        dealerFive.visibility = View.INVISIBLE
        playerOne.visibility = View.INVISIBLE
        playerTwo.visibility = View.INVISIBLE
        playerThree.visibility = View.INVISIBLE
        playerFour.visibility = View.INVISIBLE
        playerFive.visibility = View.INVISIBLE
    }

    override fun onBackPressed() {
        var intent = Intent(this,MainActivity::class.java)
        if (roundInProgress) {
            intent.putExtra("playerChips", playerChipTotal.toString())
            intent.putExtra("cardBack", dealerBackground.toString())
            startActivity(intent)
        } else {
            intent.putExtra("playerChips", ((playerChipTotal + betAmount).toString()))
            intent.putExtra("cardBack", dealerBackground.toString())
            startActivity(intent)
        }
    }

    fun launchMainMenu(view: View) {
        val intent = Intent(this,MainActivity::class.java)  // Takes user back to main menu.
        startActivity(intent)
    }

    // ToDo: Click on bet amount to clear bet and return chips to player total.

    //-------------------------- Variables reset each round.

    var roundInProgress = false
    var playerHitButton = 3    // Increments for card placement on screen, that begin at three for American Blackjack.
    var dealerHitButton = 3
    var dealerHand = ArrayDeque<String>(100)
    var playerHand = ArrayDeque<String>(100)

    //-------------------------- Variables maintained but not reset.

    var dealerBackground = 0
    var gameStart = true
    var playerChipTotal = 5000
    var betAmount = 0
    var numberOfDecks = 1
    var blankDeck = ArrayDeque<String>(52 * numberOfDecks)
    var discardPile = ArrayDeque<String>(52 * numberOfDecks)
    lateinit var shuffledCards:Array<ArrayDeque<String>>

    fun pushChipOne(view: View) {
        verifyFundsUpdateTotals(1)
        makeBetChipVisible(view)
    }

    fun pushChipFive(view: View) {
        verifyFundsUpdateTotals(5)
        makeBetChipVisible(view)
    }

    fun pushChipTwentyFive(view: View) {
        verifyFundsUpdateTotals(25)
        makeBetChipVisible(view)
    }

    fun pushChipOneHundred(view: View) {
        verifyFundsUpdateTotals(100)
        makeBetChipVisible(view)
    }

    fun pushChipFiveHundred(view: View) {
        verifyFundsUpdateTotals(500)
        makeBetChipVisible(view)
    }

    fun pushChipOneThousand(view: View) {
        verifyFundsUpdateTotals(1000)
        makeBetChipVisible(view)
    }

    fun pushChipFiveThousand(view: View) {
        verifyFundsUpdateTotals(5000)
        makeBetChipVisible(view)
    }

    fun verifyFundsUpdateTotals(amount:Int) {
        if (verifyPlayerFundsOf(amount)) {
            playerChipTotal -= amount
            betAmount += amount
            updateTotalsWhenChipAdded()
        } else {
            result.text = "Not enough funds."
        }
    }

    fun makeBetChipVisible(view: View) {
        if (betAmount > 0) {
            cbgBetChip.visibility = View.VISIBLE
            playerBet.visibility = View.VISIBLE
            result.text = ""
        }
    }

    fun verifyPlayerFundsOf(amount: Int) :Boolean {
        if (playerChipTotal >= amount) {
            return true
        }
        return false
    }

    fun updateTotalsWhenChipAdded() {
        chipTotalDisplay.text = playerChipTotal.toString()
        playerBet.text = betAmount.toString()
    }

    fun checkMinimumBetAmount():Boolean {
        return betAmount >= 5
    }

    fun chipUpdateWin() {
        playerChipTotal += betAmount
        chipTotalDisplay.text = playerChipTotal.toString()
        roundInProgress = false
    }

    fun blackjackWin() {
        var halfAmount = Math.floor(betAmount.toDouble() / 2).toInt()
        playerChipTotal += betAmount + halfAmount
        chipTotalDisplay.text = playerChipTotal.toString()
        roundInProgress = false
    }

    fun chipUpdateLose(view: View) {
        betAmount = 0
        playerBet.text = betAmount.toString()
        roundInProgress = false
        clickOnBet(view)
    }

    fun clickOnBet(view: View) {
        if (!roundInProgress) {
            playerBet.visibility = View.INVISIBLE
            cbgBetChip.visibility = View.INVISIBLE
            playerChipTotal += betAmount
            betAmount = 0
            chipTotalDisplay.text = playerChipTotal.toString()
        }
    }

    var documentRed = "#FFA00000"

    //-------------------------- Initiates beginning and each round w/variable reset

    //ToDo: Shuffle at end of deck  = is gameStart or shuffleDeck better/easier?
    fun pushDealButton(view: View) {
        roundInProgress = true
        makeCardsInvisible()
        result.text = ""
        if (checkMinimumBetAmount()) {
            if (!gameStart) {
                startNextRound(view)  // Resets variables for rounds except first
            }

            //chips.visibility = View.INVISIBLE
            deal.visibility = View.INVISIBLE
            hit.visibility = View.VISIBLE    // Button management hides deal, shows others
            stay.visibility = View.VISIBLE

            if (gameStart || shuffledCards.size <= 13) {
                gameStart = false
                blankDeck = generateDeck()
                shuffledCards = shuffleDeck(blankDeck)  // Deck generation and all shuffling
                var timesToShuffle = 1000
                while (timesToShuffle > 0) {
                    shuffledCards = shuffleDeck(ArrayDeque(shuffledCards.elementAt(0)))
                    timesToShuffle -= 1
                }
            }

            // Placing cards into dealer and player inventory (on the table).
            dealerHand.push(shuffledCards.elementAt(0).elementAt(0))
            var firstCard = calculateTotal(dealerHand)
            playerHand.push(shuffledCards.elementAt(0).elementAt(1))
            dealerHand.push(shuffledCards.elementAt(0).elementAt(2))
            playerHand.push(shuffledCards.elementAt(0).elementAt(3))
            var tempDealerTotal = calculateTotal(dealerHand) - firstCard
            dealerTotal.text = tempDealerTotal.toString()
            var removeCards = 0
            while (removeCards < 4) {
                shuffledCards.elementAt(0).pop()
                removeCards++
            }
            // Playing hand value and display
            //var tempDealerTotal = calculateTotal(dealerHand)
            var tempPlayerTotal = calculateTotal(playerHand)
            playerTotal.text = tempPlayerTotal.toString()
            tempDealerTotal = calculateTotal(dealerHand)
            // Placing cards on table.
            dealerOne.setTextSize(TypedValue.COMPLEX_UNIT_SP, 110F)
            (cardGraphic(suitIcon(dealerHand.elementAt(0)) + cardRank(dealerHand.elementAt(0)))).also { dealerOne.text = it }
            if (suitIcon(dealerHand.elementAt(0)) == "♢" || suitIcon(dealerHand.elementAt(0)) == "♡") {
                dealerOne.setTextColor(Color.parseColor(documentRed))
            } else {
                dealerOne.setTextColor(Color.parseColor("#000000"))
            }
            dealerOne.visibility = View.VISIBLE
            dealerTwo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 110F)
            (cardGraphic(suitIcon(dealerHand.elementAt(1)) + cardRank(dealerHand.elementAt(1)))).also { dealerTwo.text = it }
            if (suitIcon(dealerHand.elementAt(1)) == "♢" || suitIcon(dealerHand.elementAt(1)) == "♡") {
                dealerTwo.setTextColor(Color.parseColor(documentRed))
            } else {
                dealerTwo.setTextColor(Color.parseColor("#000000"))
            }
            //dealerTwo.visibility = View.VISIBLE
            dealerDown.visibility = View.VISIBLE
            cardBG.visibility = View.VISIBLE
            playerOne.setTextSize(TypedValue.COMPLEX_UNIT_SP,110F)
            (cardGraphic(suitIcon(playerHand.elementAt(0)) + cardRank(playerHand.elementAt(0)))).also { playerOne.text = it }
            if (suitIcon(playerHand.elementAt(0)) == "♢" || suitIcon(playerHand.elementAt(0)) == "♡") {
                playerOne.setTextColor(Color.parseColor(documentRed))
            } else {
                playerOne.setTextColor(Color.parseColor("#000000"))
            }
            playerOne.visibility = View.VISIBLE
            playerTwo.setTextSize(TypedValue.COMPLEX_UNIT_SP,110F)
            (cardGraphic(suitIcon(playerHand.elementAt(1)) + cardRank(playerHand.elementAt(1)))).also { playerTwo.text = it }
            if (suitIcon(playerHand.elementAt(1)) == "♢" || suitIcon(playerHand.elementAt(1)) == "♡") {
                playerTwo.setTextColor(Color.parseColor(documentRed))
            } else {
                playerTwo.setTextColor(Color.parseColor("#000000"))
            }
            playerTwo.visibility = View.VISIBLE
            // Immediate round results resulting from Blackjack
            if (tempDealerTotal == 21 && tempPlayerTotal == 21) {
                dealerDown.visibility = View.INVISIBLE
                cardBG.visibility = View.INVISIBLE
                dealerTwo.visibility = View.VISIBLE
                var dealerHandTotal = calculateTotal(dealerHand)
                dealerTotal.text = dealerHandTotal.toString()
                result.text = "Blackjack Push"
                showDealButtonHideOthers(view)
            } else if (tempDealerTotal == 21) {
                dealerDown.visibility = View.INVISIBLE
                cardBG.visibility = View.INVISIBLE
                dealerTwo.visibility = View.VISIBLE
                var dealerHandTotal = calculateTotal(dealerHand)
                dealerTotal.text = dealerHandTotal.toString()
                result.text = "House Wins"
                showDealButtonHideOthers(view)
                chipUpdateLose(view)
            } else if (tempPlayerTotal == 21) {
                dealerDown.visibility = View.INVISIBLE
                cardBG.visibility = View.INVISIBLE
                dealerTwo.visibility = View.VISIBLE
                result.text = "Player Wins"
                var dealerHandTotal = calculateTotal(dealerHand)
                dealerTotal.text = dealerHandTotal.toString()
                showDealButtonHideOthers(view)
                blackjackWin()
            }
            // pushHitButton() or pushStayButton() next
        } else {
            result.text = "Minimum Bet $5"
            roundInProgress = false
        }
    }

    fun showDealButtonHideOthers(view: View) {
        //chips.visibility = View.VISIBLE
        deal.visibility = View.VISIBLE
        hit.visibility = View.INVISIBLE
        stay.visibility = View.INVISIBLE
    }

    fun pushHitButton(view: View) {
        // Adds a card
        playerHand.push(shuffledCards.elementAt(0).elementAt(0))
        shuffledCards.elementAt(0).pop()
        var playerHandTotal = calculateTotal(playerHand)
        // Display to user
        playerTotal.text = playerHandTotal.toString()
        // Adding the card to the correct card spot for display
        if (playerHitButton == 3) {
            (cardGraphic(suitIcon(playerHand.elementAt(0)) + cardRank(playerHand.elementAt(0)))).also { playerThree.text = it }
            if (suitIcon(playerHand.elementAt(0)) == "♢" || suitIcon(playerHand.elementAt(0)) == "♡") {
                playerThree.setTextColor(Color.parseColor(documentRed))
            } else {
                playerThree.setTextColor(Color.parseColor("#000000"))
            }
            playerThree.visibility = View.VISIBLE
            playerHitButton++
        } else if (playerHitButton == 4) {
            (cardGraphic(suitIcon(playerHand.elementAt(0)) + cardRank(playerHand.elementAt(0)))).also { playerFour.text = it }
            if (suitIcon(playerHand.elementAt(0)) == "♢" || suitIcon(playerHand.elementAt(0)) == "♡") {
                playerFour.setTextColor(Color.parseColor(documentRed))
            } else {
                playerFour.setTextColor(Color.parseColor("#000000"))
            }
            playerFour.visibility = View.VISIBLE
            playerHitButton++
        } else {
            (cardGraphic(suitIcon(playerHand.elementAt(0)) + cardRank(playerHand.elementAt(0)))).also { playerFive.text = it }
            if (suitIcon(playerHand.elementAt(0)) == "♢" || suitIcon(playerHand.elementAt(0)) == "♡") {
                playerFive.setTextColor(Color.parseColor(documentRed))
            } else {
                playerFive.setTextColor(Color.parseColor("#000000"))
            }
            playerFive.visibility = View.VISIBLE
            playerHitButton++
        }
        // Player results: Bust, Blackjack w/dealer results, or fall through to hit or stay
        if (playerHandTotal > 21) {
            dealerDown.visibility = View.INVISIBLE
            cardBG.visibility = View.INVISIBLE
            dealerTwo.visibility = View.VISIBLE
            var dealerHandTotal = calculateTotal(dealerHand)
            dealerTotal.text = dealerHandTotal.toString()
            result.text = "Bust"
            showDealButtonHideOthers(view)
            chipUpdateLose(view)
        } else if (playerHandTotal == 21) {
            playDealerCards(view)
            // Effectively pauses gameplay for review of results
            showDealButtonHideOthers(view)
        }
    }

    fun pushStayButton(view: View) {
        // Player stands on results, dealer replies
        playDealerCards(view)
        // Effectively a pause for player to absorb results.
        showDealButtonHideOthers(view)
    }

    fun startNextRound(view: View) {
        // Resets all default variables
        var playerCardsIncrement = 0
        while (playerCardsIncrement < playerHand.size) {
            discardPile.push(playerHand.elementAt(0))
            playerHand.pop()
            playerCardsIncrement++
        }                                                      // Places player/dealer cards in discard.
        var dealerCardsIncrement = 0
        while (dealerCardsIncrement < dealerHand.size) {
            discardPile.push(dealerHand.elementAt(0))
            dealerHand.pop()
            dealerCardsIncrement++
        }

        playerHitButton = 3                                    // Resets index of where new cards go.
        dealerHitButton = 3

        result.text = ""
        val cardsOnTable = arrayOf(playerOne, playerTwo, playerThree, playerFour, playerFive,
            dealerOne, dealerTwo, dealerThree, dealerFour, dealerFive)
        var tableIncrement = 0
        while (tableIncrement < cardsOnTable.size) {           // Clearing cards from table.
            cardsOnTable[tableIncrement].text = ""
            tableIncrement++
        }
        dealerTotal.text = ""
        playerTotal.text = ""                                  // Clearing announcements from table.
        dealerHand = ArrayDeque<String>(100)
        playerHand = ArrayDeque<String>(100)       // Resetting player and dealer hands
    }

    fun playDealerCards(view: View) {
        dealerDown.visibility = View.INVISIBLE
        cardBG.visibility = View.INVISIBLE
        dealerTwo.visibility = View.VISIBLE
        var dealerHandTotal = calculateTotal(dealerHand)
        dealerTotal.text = dealerHandTotal.toString()
        while (dealerHandTotal < 17) {
            // Putting a new card in dealers hand and removing a card from the shuffled deck.
            dealerHand.push(shuffledCards.elementAt(0).elementAt(0))
            shuffledCards.elementAt(0).pop()
            dealerHandTotal = calculateTotal(dealerHand)
            // Displaying total to screen and placing cards in the correct spot.
            dealerTotal.text = dealerHandTotal.toString()
            if (dealerHitButton == 3) {
                (cardGraphic(suitIcon(dealerHand.elementAt(0)) + cardRank(dealerHand.elementAt(0)))).also { dealerThree.text = it }
                if (suitIcon(dealerHand.elementAt(0)) == "♢" || suitIcon(dealerHand.elementAt(0)) == "♡") {
                    dealerThree.setTextColor(Color.parseColor(documentRed))
                } else {
                    dealerThree.setTextColor(Color.parseColor("#000000"))
                }
                dealerThree.visibility = View.VISIBLE
                dealerHitButton++
            } else if (dealerHitButton == 4) {
                (cardGraphic(suitIcon(dealerHand.elementAt(0)) + cardRank(dealerHand.elementAt(0)))).also { dealerFour.text = it }
                if (suitIcon(dealerHand.elementAt(0)) == "♢" || suitIcon(dealerHand.elementAt(0)) == "♡") {
                    dealerFour.setTextColor(Color.parseColor(documentRed))
                } else {
                    dealerFour.setTextColor(Color.parseColor("#000000"))
                }
                dealerFour.visibility = View.VISIBLE
                dealerHitButton++
            } else if (dealerHitButton == 5) {
                (cardGraphic(suitIcon(dealerHand.elementAt(0)) + cardRank(dealerHand.elementAt(0)))).also { dealerFive.text = it }
                if (suitIcon(dealerHand.elementAt(0)) == "♢" || suitIcon(dealerHand.elementAt(0)) == "♡") {
                    dealerFive.setTextColor(Color.parseColor(documentRed))
                } else {
                    dealerFive.setTextColor(Color.parseColor("#000000"))
                }
                dealerFive.visibility = View.VISIBLE
                dealerHitButton++
            }
        }
        // Displaying game results to player.
        var playerHandTotal = calculateTotal(playerHand)
        if (dealerHandTotal == 21 && playerHandTotal == 21) {
            result.text = "BlackJack Push"
        } else if (dealerHandTotal > 21) {
            result.text = "Player Wins"
            chipUpdateWin()
        } else if (dealerHandTotal > playerHandTotal) {
            result.text = "House Wins"
            chipUpdateLose(view)
        } else if (dealerHandTotal == playerHandTotal) {
            result.text = "Push"
        } else {
            result.text = "Player Wins"
            chipUpdateWin()
        }
    }

    fun calculateTotal(hand:ArrayDeque<String>): Int {
        // Counts number of aces while counting total of all other cards, then finds the best value of all aces.
        var totalOfAllCards = 0
        var numberOfAces = 0
        var increment = 0
        while (increment < hand.size) {
            var cardValue = countCards(hand.elementAt(increment))  // Finds the value of each card in a hand.
            if (cardValue == 1) {
                numberOfAces++
            } else {
                totalOfAllCards += cardValue
            }
            increment++
        }
        // Only one ace can equal eleven, numberOfAces + 10 + totalOfAllCards is max, numberOfAces + totalOfAllCards is min
        if (numberOfAces > 0) {
            return if (totalOfAllCards + 10 + numberOfAces <= 21) {
                totalOfAllCards + 10 + numberOfAces
            } else {
                totalOfAllCards + numberOfAces
            }
        }
        return totalOfAllCards
    }

    fun countCards(card: String): Int {
        // Takes one card and finds its value
        var singleCardValue = 0
        when {
            "A".toRegex().containsMatchIn(card) -> {
                singleCardValue += 1
            }
            "K".toRegex().containsMatchIn(card) -> {
                singleCardValue += 10
            }
            "Q".toRegex().containsMatchIn(card) -> {
                singleCardValue += 10
            }
            "J".toRegex().containsMatchIn(card) -> {
                singleCardValue += 10
            }
            "10".toRegex().containsMatchIn(card) -> {
                singleCardValue += 10
            }
            "9".toRegex().containsMatchIn(card) -> {
                singleCardValue += 9
            }
            "8".toRegex().containsMatchIn(card) -> {
                singleCardValue += 8
            }
            "7".toRegex().containsMatchIn(card) -> {
                singleCardValue += 7
            }
            "6".toRegex().containsMatchIn(card) -> {
                singleCardValue += 6
            }
            "5".toRegex().containsMatchIn(card) -> {
                singleCardValue += 5
            }
            "4".toRegex().containsMatchIn(card) -> {
                singleCardValue += 4
            }
            "3".toRegex().containsMatchIn(card) -> {
                singleCardValue += 3
            }
            "2".toRegex().containsMatchIn(card) -> {
                singleCardValue += 2
            }
        }
        return singleCardValue
    }

    fun cardRank(card: String): String {
        // Takes one card and finds its Rank
        var singleCardValue: String = ""
        when {
            "A".toRegex().containsMatchIn(card) -> {
                singleCardValue += "A"
            }
            "K".toRegex().containsMatchIn(card) -> {
                singleCardValue += "K"
            }
            "Q".toRegex().containsMatchIn(card) -> {
                singleCardValue += "Q"
            }
            "J".toRegex().containsMatchIn(card) -> {
                singleCardValue += "J"
            }
            "10".toRegex().containsMatchIn(card) -> {
                singleCardValue += "10"
            }
            "9".toRegex().containsMatchIn(card) -> {
                singleCardValue += "9"
            }
            "8".toRegex().containsMatchIn(card) -> {
                singleCardValue += "8"
            }
            "7".toRegex().containsMatchIn(card) -> {
                singleCardValue += "7"
            }
            "6".toRegex().containsMatchIn(card) -> {
                singleCardValue += "6"
            }
            "5".toRegex().containsMatchIn(card) -> {
                singleCardValue += "5"
            }
            "4".toRegex().containsMatchIn(card) -> {
                singleCardValue += "4"
            }
            "3".toRegex().containsMatchIn(card) -> {
                singleCardValue += "3"
            }
            "2".toRegex().containsMatchIn(card) -> {
                singleCardValue += "2"
            }
        }
        return singleCardValue
    }

    fun suitIcon(card: String): String{
        var singleCardSuit: String = ""
        when {
            "S".toRegex().containsMatchIn(card) -> {
                singleCardSuit += "♠"
            }
            "C".toRegex().containsMatchIn(card) -> {
                singleCardSuit += "♣"
            }
            "D".toRegex().containsMatchIn(card) -> {
                singleCardSuit += "♢"
            }
            "H".toRegex().containsMatchIn(card) -> {
                singleCardSuit += "♡"
            }
        }
        return singleCardSuit
    }

    fun generateDeck(): ArrayDeque<String> {
        // Creates the playing deck(s) for the game
        val suits = arrayOf("Spade", "Diamonds", "Clubs", "Hearts")
        val royalty = arrayOf("J", "Q", "K")
        val countCard = arrayOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        val newDeck = ArrayDeque<String>(52)
        var numberOfSuits = 0
        var numberOfRoyalty = 0
        var numberOfCountCards = 9
        var cardCount = 0
        // Creates each suit with royalty and count cards
        while (numberOfSuits < 2) {
            while (numberOfCountCards >= 0 ) {
                newDeck.push(suits[numberOfSuits] + countCard[numberOfCountCards])
                numberOfCountCards -= 1
                cardCount += 1
            }
            while (numberOfRoyalty < 3) {
                newDeck.push(suits[numberOfSuits] + royalty[numberOfRoyalty])
                numberOfRoyalty += 1
                cardCount += 1
            }
            numberOfRoyalty = 0
            numberOfCountCards = 8
            numberOfSuits += 1
        }
        countCard.reverse()
        royalty.reverse()
        while (numberOfSuits < 4) {
            while (numberOfRoyalty < 3) {
                newDeck.push(suits[numberOfSuits] + royalty[numberOfRoyalty])
                numberOfRoyalty += 1
                cardCount += 1
            }
            while (numberOfCountCards >= 0 ) {
                newDeck.push(suits[numberOfSuits] + countCard[numberOfCountCards])
                numberOfCountCards -= 1
                cardCount += 1
            }
            numberOfRoyalty = 0
            numberOfCountCards = 8
            numberOfSuits += 1
        }
        // println(newDeck)
        return newDeck
    }

    fun shuffleDeck(deck: ArrayDeque<String>): Array<ArrayDeque<String>> {
        // All calls for shuffling the deck(s) handled here.
        val twoHalves = divideDeck(deck)
        val halfOne = twoHalves[0]
        val halfTwo = twoHalves[1]
        return arrayOf(riffleShuffleTight(halfOne, halfTwo))
    }

    fun divideDeck(deck: ArrayDeque<String>): Array<ArrayDeque<String>> {
        // Approximately divides the deck in two.
        val cutPoint = ((deck.size / 2) - 4) + floor(Math.random() * 8).toInt()
        val leftDeck = ArrayDeque<String>(cutPoint)
        val rightDeck = ArrayDeque<String>(cutPoint)
        var increment = 0
        while (increment < cutPoint) {
            val removedCard = deck.elementAt(increment)
            leftDeck.push(removedCard)
            increment++
        }
        while (increment < deck.size) {
            val removedCard = deck.elementAt(increment)
            rightDeck.push(removedCard)
            increment++
        }
        return arrayOf(leftDeck, rightDeck)
    }

    fun riffleShuffleTight(leftDeck: ArrayDeque<String>, rightDeck: ArrayDeque<String>): ArrayDeque<String> {
        // Shuffles deck with a tight riffle.
        // ToDo: Check top of deck is shuffled with the bottom of deck correctly
        val returnedDeck = ArrayDeque<String>(leftDeck.size + rightDeck.size)

        var leftRight = true
        var i = 0
        while (i < 500)  {
            val numberOfCards = (floor(Math.random() * 2) + 1).toInt()
            if (leftRight && leftDeck.size > 0) {
                var n = 0
                while (n < numberOfCards) {
                    if (leftDeck.size > 0) {
                        val removedCard = leftDeck.pop()
                        returnedDeck.push(removedCard)
                    }
                    n++
                }
            } else if (!leftRight && rightDeck.size > 0) {
                var x = 0
                while (x < numberOfCards) {
                    if (rightDeck.size > 0) {
                        val removedCard = rightDeck.pop()
                        returnedDeck.push(removedCard)
                    }
                    x++
                }
            }
            if (leftRight) {
                leftRight = false
            } else if (!leftRight) {
                leftRight = true
            }
            i += numberOfCards
        }
        return returnedDeck
    }

    fun cardGraphic(suitAndRank: String): String { // ♡♢♣♠  s h d c
        var result: String = ""
        val arrayOfNames = arrayOf(
            "♠A", "♠2", "♠3", "♠4", "♠5", "♠6", "♠7", "♠8", "♠9", "♠10", "♠J", "♠Q", "♠K",
            "♡A", "♡2", "♡3", "♡4", "♡5", "♡6", "♡7", "♡8", "♡9", "♡10", "♡J", "♡Q", "♡K",
            "♢A", "♢2", "♢3", "♢4", "♢5", "♢6", "♢7", "♢8", "♢9", "♢10", "♢J", "♢Q", "♢K",
            "♣A", "♣2", "♣3", "♣4", "♣5", "♣6", "♣7", "♣8", "♣9", "♣10", "♣J", "♣Q", "♣K")
        val playingCard = arrayOf("🂡","🂢","🂣","🂤","🂥","🂦","🂧","🂨","🂩","🂪","🂫","🂭","🂮","🂱","🂲","🂳",
            "🂴","🂵","🂶","🂷","🂸","🂹","🂺","🂻","🂽","🂾","🃁","🃂","🃃","🃄","🃅","🃆","🃇","🃈","🃉","🃊","🃋",
            "🃍","🃎","🃑","🃒","🃓","🃔","🃕","🃖","🃗","🃘","🃙","🃚","🃛","🃝","🃞")
        var increment = 0
        while (increment < 52) {
            if (arrayOfNames[increment] == suitAndRank) {
                result = playingCard[increment]
            }
            increment++
        }
        return result
    }
}
