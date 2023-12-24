package db

import android.content.Context
import androidx.room.Room

class RoomInstance {
    companion object{
        var customerDb:CustomerDb?=null
        fun getInstance(context: Context):CustomerDb{
            if (customerDb==null){
                customerDb=Room.databaseBuilder(
                    context,
                    CustomerDb::class.java,
                    "CustomerDb"
                ).build()
            }
            return customerDb!!
        }
    }
}