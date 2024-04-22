package tsykarev.grigorii.memorypuzzle.screens.game

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tsykarev.grigorii.memorypuzzle.database.GameScore
import tsykarev.grigorii.memorypuzzle.database.MemoryPuzzleDao

class GameViewModel(private val dao: MemoryPuzzleDao,
                    application: Application) : AndroidViewModel(application) {
    init {
        Log.i("GameViewModel", "GameViewModel created")
        initializeScore()
    }

    private fun initializeScore() {
        uiScope.launch {
            score.value = getScoreFromDatabase()
        }
    }

    private suspend fun getScoreFromDatabase(): GameScore? {
        return withContext(Dispatchers.IO) {
            var currScore = dao.getScore()
            currScore
        }
    }

    fun onStopTracking() {
        uiScope.launch {
            val newScore = GameScore(gameDifficulty = currDifficulty, moves = currMoves)
            insert(newScore)
            score.value = getScoreFromDatabase()
        }
    }

    private suspend fun insert(score: GameScore) {
        withContext(Dispatchers.IO) {
            dao.insert(score)
        }
    }

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)
    private var score = MutableLiveData<GameScore?>()
    private val allScores = dao.getAllScores()
    private val currDifficulty: Int = 0
    private var currMoves: Int = 0

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        Log.i("GameViewModel", "GameViewModel destroyed")
    }
}