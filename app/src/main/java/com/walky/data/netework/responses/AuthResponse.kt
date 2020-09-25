package com.walky.data.netework.responses

import com.walky.data.db.enttities.User

data class AuthResponse (
    val status_code: Int?,
    val message: String?,
    val data : Data?,


)
class Data(
    val access_token: String?,
    val token_type: String?,
    val user_info: User?,
)
