package com.walky.data.db.enttities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_table")
data class Message (


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id : Int,

    @ColumnInfo(name = "message")
    var message: String  ,

    @ColumnInfo(name = "time")
    var timeMsg: String
)