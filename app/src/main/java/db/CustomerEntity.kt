package db

import android.widget.EditText
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity("Customers")
data class CustomerEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var name:String,
    var surName:String,
    var age: String
)