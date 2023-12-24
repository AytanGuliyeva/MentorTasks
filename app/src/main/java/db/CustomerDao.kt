package db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CustomerDao {
    @Insert
    fun insert(entity: CustomerEntity):Long

    @Query("SELECT * FROM Customers")
    suspend fun getAll():MutableList<CustomerEntity>

    @Update
    fun update(entity: CustomerEntity)

    @Query("DELETE FROM Customers WHERE id = :id")
    fun delete(id: Int)
}