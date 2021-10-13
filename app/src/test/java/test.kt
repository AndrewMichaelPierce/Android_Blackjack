//import com.example.pierceandrew_courseproject.divideDeck
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Assert
import java.util.ArrayDeque
import kotlin.math.floor


fun main(args: Array<String>) {

    var string = "1115000"
    string.toCharArray()
    var amount = ""
    var beginning = ""
    var increment = 0
    while (increment < string.length) {
        if (increment >= 3) {
            amount += string[increment]
        } else {
            beginning += string[increment]
        }
        increment++
    }
    var newAmount = amount.toInt()
    newAmount++
    newAmount.toString()
    var result = beginning
    increment = 0
    while (increment < newAmount.toString().length){
        result += newAmount.toString()[increment]
        increment++
    }

    println(string)
    println(amount)
    println(result)
    /*
    var blankDeck = generateDeck()
    var shuffledCards = shuffleDeck(blankDeck)

    var timesToShuffle = 1000
    while (timesToShuffle > 0) {
        shuffledCards = shuffleDeck(ArrayDeque(shuffledCards.elementAt(0)))
        timesToShuffle -= 1
    }

    var cardTotal = 0
    println("deck" + shuffledCards.elementAt(0))
    println("Size" + shuffledCards.elementAt(0).size)
    println("Hello"+ shuffledCards.elementAt(0).elementAt(0))
    cardTotal = countCards(shuffledCards.elementAt(0).elementAt(0))
    println(cardTotal)

     */
}
/*
fun countCards(card: String): Int {
    var cardValueOne = 0
    var ace = "A".toRegex()
    var king = "K".toRegex()
    var queen = "Q".toRegex()
    var jack = "J".toRegex()
    var ten = "10".toRegex()
    var nine = "9".toRegex()
    var eight = "8".toRegex()
    var seven = "7".toRegex()
    var six = "6".toRegex()
    var five = "5".toRegex()
    var four = "4".toRegex()
    var three = "3".toRegex()
    var two = "2".toRegex()
    if (king.containsMatchIn(card)) {
        println("KING FOUND")
    }
    if (ace.containsMatchIn(card)) {
        println("Ace")
        cardValueOne += 1
    } else if (king.containsMatchIn(card)) {
        println("King")
        cardValueOne += 10
    } else if (queen.containsMatchIn(card)) {
        println("Queen")
        cardValueOne += 10
    } else if (jack.containsMatchIn(card)) {
        println("Jack")
        cardValueOne += 10
    } else if (ten.containsMatchIn(card)) {
        println("10")
        cardValueOne += 10
    } else if (nine.containsMatchIn(card)) {
        println("9")
        cardValueOne += 9
    } else if (eight.containsMatchIn(card)) {
        println("8")
        cardValueOne += 8
    } else if (seven.containsMatchIn(card)) {
        println("7")
        cardValueOne += 7
    } else if (six.containsMatchIn(card)) {
        println("6")
        cardValueOne += 6
    } else if (five.containsMatchIn(card)) {
        println("5")
        cardValueOne += 5
    } else if (four.containsMatchIn(card)) {
        println("4")
        cardValueOne += 4
    } else if (three.containsMatchIn(card)) {
        println("3")
        cardValueOne += 3
    } else if (two.containsMatchIn(card)) {
        println("2")
        cardValueOne += 2
    }
    return cardValueOne
}

fun generateDeck(): ArrayDeque<String> {
    var suits = arrayOf("Spade", "Clubs", "Hearts", "Diamonds")
    var royalty = arrayOf("A", "K", "Q", "J")
    var countCard = arrayOf("2", "3", "4", "5", "6", "7", "8", "9", "10")
    var newDeck = ArrayDeque<String>(52)
    var numberOfSuits = 0
    var numberOfRoyalty = 0
    var numberOfCountCards = 8
    var cardCount = 0
    while (numberOfSuits < 4) {
        while (numberOfRoyalty < 4) {
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
    return newDeck
}

fun shuffleDeck(deck: ArrayDeque<String>): Array<ArrayDeque<String>> {
    var twoHalves = divideDeck(deck)
    var halfOne = twoHalves[0]
    var halfTwo = twoHalves[1]
    return arrayOf(skinnyShuffle(halfOne, halfTwo))
}

fun divideDeck(deck: ArrayDeque<String>): Array<ArrayDeque<String>> {
    var cutPoint = (deck.size / 2 - (4)) + floor(Math.random() * 8).toInt()
    println(cutPoint)
    var leftDeck = ArrayDeque<String>(cutPoint)
    var rightDeck = ArrayDeque<String>(cutPoint)
    var increment = 0
    while (increment < cutPoint) {
        var removedCard = deck.elementAt(increment)
        leftDeck.push(removedCard)
        increment++
    }
    while (increment < deck.size) {
        var removedCard = deck.elementAt(increment)
        rightDeck.push(removedCard)
        increment++
    }
    return arrayOf(leftDeck, rightDeck)
}

fun skinnyShuffle(leftDeck: ArrayDeque<String>, rightDeck: ArrayDeque<String>): ArrayDeque<String> {
    var returnedDeck = ArrayDeque<String>(leftDeck.size + rightDeck.size)

    var leftRight = true
    var i = 0
    while (i < 500)  {
        var numberOfCards = (floor(Math.random() * 2) + 1).toInt()
        if (leftRight && leftDeck.size > 0) {
            var n = 0
            while (n < numberOfCards) {
                if (leftDeck.size > 0) {
                    var removedCard = leftDeck.pop()
                    returnedDeck.push(removedCard);
                }
                n++
            }
        } else if (!leftRight && rightDeck.size > 0) {
            var x = 0
            while (x < numberOfCards) {
                if (rightDeck.size > 0) {
                    var removedCard = rightDeck.pop()
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
    println("returnedDeckSize" + returnedDeck.size)
    return returnedDeck
}

 */

var cardType = 0

fun getBackType(): Int {
    return cardType
}

fun setBackType(typeOfCard: Int) {
    cardType = typeOfCard
}



