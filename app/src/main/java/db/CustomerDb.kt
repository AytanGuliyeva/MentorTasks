package db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CustomerEntity::class],
    version = 1
)
abstract class CustomerDb :RoomDatabase(){

    abstract fun customerDao():CustomerDao

}