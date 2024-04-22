package tsykarev.grigorii.memorypuzzle.models

import tsykarev.grigorii.memorypuzzle.utils.DEFAULT_ICONS

class MemoryGame(private val boardSize: BoardSize) {
    val cards: List<MemoryCard>

    var numPairsFound = 0

    private var indexOfSingleSelectedCard: Int? = null

    private var numCardFlips: Int = 0

    init {
        val chosenImages = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        var randomizeImages = (chosenImages + chosenImages).shuffled()
        cards = randomizeImages.map { MemoryCard(it) }
    }

    fun flipCard(position: Int): Boolean {
        numCardFlips++
        val card = cards[position]

        var foundMath = false

        if (indexOfSingleSelectedCard == null){
            restoreCards()

            indexOfSingleSelectedCard = position
        } else{
            foundMath = checkForMatch(indexOfSingleSelectedCard!!, position)

          indexOfSingleSelectedCard = null
        }

        card.isFaceUp = !card.isFaceUp

        return foundMath
    }

    private fun checkForMatch(position1: Int, position2: Int): Boolean {
        if (cards[position1].identifier != cards[position2].identifier) {
            return false
        }
        cards[position1].isMatched = true
        cards[position2].isMatched = true
        numPairsFound++
        return true
    }

    private fun restoreCards() {
        for(card in cards){
            if(!card.isMatched){
                card.isFaceUp = false
            }
        }
    }

    fun haveWonGame(): Boolean {
        return numPairsFound == boardSize.getNumPairs()
    }

    fun isCardFaceUp(position: Int): Boolean {
        return cards[position].isFaceUp
    }

    fun getNumMoves(): Int {
        return numCardFlips / 2
    }
}
