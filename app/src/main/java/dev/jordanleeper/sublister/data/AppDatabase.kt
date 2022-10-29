package dev.jordanleeper.sublister.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Sublist::class, Item::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun listDao(): ListDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE != null) {
                return INSTANCE!!
            }

            synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "list"
                ).build()
                return INSTANCE!!
            }
        }
    }
}