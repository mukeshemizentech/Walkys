package com.walky.data.db.enttities

import androidx.room.Entity

@Entity
data class User(
    val id: Int? = null,
    val email: String? = null,
    val full_name: String? = null,
    val image: String? = null,
    val otp_verification: String? = null,
    )