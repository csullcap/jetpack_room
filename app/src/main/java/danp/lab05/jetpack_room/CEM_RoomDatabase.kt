package danp.lab05.jetpack_room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(CEM::class)], version = 1)
abstract class CEM_RoomDatabase : RoomDatabase() {

    abstract fun CEMDao(): CEM_Dao

    companion object {

        private var INSTANCE: CEM_RoomDatabase? = null

        fun getInstance(context: Context): CEM_RoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CEM_RoomDatabase::class.java,
                        "CEM_DB"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}