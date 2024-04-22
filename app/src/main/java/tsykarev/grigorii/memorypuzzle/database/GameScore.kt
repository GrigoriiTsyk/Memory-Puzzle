package tsykarev.grigorii.memorypuzzle.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_score_table")
data class GameScore(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var gameId: Long = 0L,

    @ColumnInfo(name = "end_time_millis")
    var endTimeMillis: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "difficulty")
    var gameDifficulty: Int = 0,

    @ColumnInfo(name = "moves")
    var moves: Int = 0
)
