package tsykarev.grigorii.memorypuzzle.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GameScore::class], version = 1, exportSchema = false)
abstract class MemoryPuzzleDatabase : RoomDatabase() {

    abstract fun getMemoryPuzzleDao(): MemoryPuzzleDao

    companion object {
        @Volatile
        private var INSTANCE: MemoryPuzzleDatabase? = null

        fun getInstance(context: Context): MemoryPuzzleDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        MemoryPuzzleDatabase::class.java, "sleep_tracker_db")
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}