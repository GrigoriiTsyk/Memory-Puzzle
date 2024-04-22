package tsykarev.grigorii.memorypuzzle.screens.game

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tsykarev.grigorii.memorypuzzle.database.MemoryPuzzleDao

class GameViewModelFactory(
    private val dao: MemoryPuzzleDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(dao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}