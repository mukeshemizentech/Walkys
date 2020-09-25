package com.walky.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.walky.data.db.enttities.Message

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setMessage(message:Message)

    @Query("Select * from message_table ORDER BY id ASC")
    fun getMessage() : LiveData<List<Message>>


    @Query("DELETE FROM message_table")
    fun deleteAll()

}