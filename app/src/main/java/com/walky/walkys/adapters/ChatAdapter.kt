package com.walky.walkys.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.walky.data.db.enttities.Message
import com.walky.responses.HomeResponse
import com.walky.walkys.R
import com.walky.walkys.ui.chat.ChatActivity.Companion.isGroup


class ChatAdapter(context: Context, messageList: List<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val messageList: List<Message> = messageList
    private var context: Context = context

    companion object {
        private const val VIEW_TYPE_MESSAGE_SENT = 1
        private const val VIEW_TYPE_MESSAGE_RECEIVED = 2
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    // Determines the appropriate ViewType according to the sender of the message.
    override fun getItemViewType(position: Int): Int {
//        val message: UserMessage = mMessageList[position] as UserMessage
        return if (position%2==0) {
           VIEW_TYPE_MESSAGE_SENT
        } else  {
            // If some other user sent the message
            VIEW_TYPE_MESSAGE_RECEIVED
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.sender_layout, parent, false)
            SentMessageHolder(view)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.received_layout, parent, false)
            ReceivedMessageHolder(view)
        }
//        return
    }

    //    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        TODO("Not yet implemented")
//    }
    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message: Message = messageList[position]
        when (holder.itemViewType) {
            VIEW_TYPE_MESSAGE_SENT -> (holder as SentMessageHolder?)!!.bind(message)
            VIEW_TYPE_MESSAGE_RECEIVED -> (holder as ReceivedMessageHolder?)!!.bind(message)
        }
    }

    private inner class SentMessageHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var messageText: TextView = itemView.findViewById<View>(R.id.textMessageBody) as TextView
        var timeText: TextView = itemView.findViewById<View>(R.id.textMessageTime) as TextView
        fun bind(message: Message) {
            messageText.text = message.message
            timeText.text=message.timeMsg
        }

    }

    private inner class ReceivedMessageHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var messageText: TextView = itemView.findViewById<View>(R.id.textMessageBody) as TextView
        var timeText: TextView = itemView.findViewById<View>(R.id.textMessageTime) as TextView
        var nameText: TextView = itemView.findViewById<View>(R.id.textMessageName) as TextView
        var profileImage: RoundedImageView =
            itemView.findViewById<View>(R.id.imageMessageProfile) as RoundedImageView

        fun bind(message: Message) {
            messageText.text = message.message
            timeText.text=message.timeMsg
            if (isGroup=="2"|| isGroup=="4") {
                profileImage.visibility = View.VISIBLE
                nameText.visibility=View.VISIBLE
            }else{
                profileImage.visibility = View.GONE
                nameText.visibility=View.GONE
            }
//
//            // Format the stored timestamp into a readable String using method.
//            timeText.setText(Utils.formatDateTime(message.getCreatedAt()))
//            nameText.setText(message.getSender().getNickname())
//
//            // Insert the profile image from the URL into the ImageView.
//            Utils.displayRoundImageFromUrl(
//                mContext,
//                message.getSender().getProfileUrl(),
//                profileImage
//            )
        }

    }


}