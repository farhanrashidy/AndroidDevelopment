package ca.gbc.comp3074.lab04

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1)
abstract class ItemsDatabase: RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object{
        @Volatile
        private var INSTANCE: ItemsDatabase? = null

        fun getDatabase(context: Context): ItemsDatabase{
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemsDatabase::class.java,
                    "items database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}