package tsykarev.grigorii.memorypuzzle.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface MemoryPuzzleDao {
    @Insert
    fun insert(gameScore: GameScore)

    @Update
    fun update(gameScore: GameScore)

    @Query("SELECT * FROM game_score_table WHERE id = :key")
    fun get(key: Long): GameScore?

    @Query("SELECT * FROM game_score_table ORDER BY id DESC")
    fun getAllScores(): LiveData<List<GameScore>>

    @Query("SELECT * FROM game_score_table ORDER BY id DESC LIMIT 1")
    fun getScore(): GameScore?

    @Query("DELETE FROM game_score_table")
    fun clear()
}