package com.walky.walkys.ui.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.walky.data.db.enttities.Message
import com.walky.data.repository.MessageRepository

class MessageViewModel(application: Application):AndroidViewModel(application){


    private var repository : MessageRepository = MessageRepository(application)

    fun getMessages() = repository.getMessages()

    fun deleteAll() = repository.deleteAll()

    fun setMessages(message : Message) {repository.setMessages(message)}
}