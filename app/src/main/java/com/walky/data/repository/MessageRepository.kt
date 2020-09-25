package com.walky.data.repository

import android.app.Application
import com.walky.data.db.dao.MessageDao
import com.walky.data.db.database.MessageDatabase
import com.walky.data.db.enttities.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MessageRepository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var messageDao: MessageDao?

    init {
        val db = MessageDatabase.getDatabase(application)
        messageDao = db?.messageDao()
    }

    fun getMessages() = messageDao?.getMessage()

    fun deleteAll() = messageDao?.deleteAll()

    fun setMessages(message : Message){
        launch {
            setMessagesBG(message)
        }
    }

    private suspend fun setMessagesBG(message: Message) {
       withContext(Dispatchers.IO){
           messageDao?.setMessage(message)
       }
    }

}