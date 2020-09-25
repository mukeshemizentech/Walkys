package com.walky.walkys.ui.chat

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.walky.data.db.enttities.Message
import com.walky.utils.Utils
import com.walky.utils.toast
import com.walky.walkys.R
import com.walky.walkys.activities.NewPostActivity
import com.walky.walkys.adapters.ChatAdapter
import com.walky.walkys.databinding.ActivityChatBinding
import com.walky.walkys.databinding.PopupLayoutBinding


class ChatActivity : AppCompatActivity() {


    lateinit var chatAdapter: ChatAdapter
    private var messageList: ArrayList<Message> = ArrayList()

    private var messageViewModel: MessageViewModel? = null
    lateinit var layoutManager: LinearLayoutManager

    lateinit var binding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)

        messageViewModel = ViewModelProviders.of(this).get(MessageViewModel::class.java)
        messageViewModel?.getMessages()?.observe(this, Observer<List<Message>> {
            this.renderMessages(it)
        })



        binding.header.titleTv.text = "Carlos Micbride"
        binding.header.optionMenu.setImageResource(R.drawable.add_post)
        binding.header.optionMenu.visibility = View.VISIBLE
        binding.header.homeMenu.setImageResource(R.drawable.back)
        Utils.tintColor(this, binding.header.homeMenu, R.color.white)
        binding.header.homeMenu.setOnClickListener { finish() }

        binding.header.optionMenu.setOnClickListener {
            startActivity(Intent(this@ChatActivity, NewPostActivity::class.java))
        }

        binding.chatLayout.addIv.setOnClickListener {
            displayPopupWindow(it)
        }

        // Send button sends a message and clears the EditText
        binding.chatLayout.sendIv.setOnClickListener {

            if (binding.chatLayout.saySomething.text.isNotEmpty()) {
                val message = Message(
                    0, binding.chatLayout.saySomething.text.toString(), Utils.currentTime(
                        "hh:mm"
                    )!!
                )
                messageViewModel?.setMessages(message)
                // Clear input box
                binding.chatLayout.saySomething.setText("")
            } else {
                toast("Please write something!!")
            }
        }


        chatAdapter = ChatAdapter(this, messageList!!)
        layoutManager = LinearLayoutManager(this)
        layoutManager.setReverseLayout(false)
        layoutManager.setStackFromEnd(true)
        layoutManager.isSmoothScrollbarEnabled = true
        binding.recyclerView.layoutManager = layoutManager

        binding.recyclerView.addOnLayoutChangeListener(View.OnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (bottom < oldBottom) {
                binding.recyclerView.postDelayed(
                    Runnable { binding.recyclerView.smoothScrollToPosition(chatAdapter.getItemCount()) },
                    100
                )
            }
        })

    }

    var popup = PopupWindow()
    private fun displayPopupWindow(anchorView: View) {
        popup = PopupWindow(this)
        val popupLayoutBinding: PopupLayoutBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.popup_layout,
            null,
            false
        )
        popup.contentView = popupLayoutBinding.root
        // Set content width and height
        popup.height = WindowManager.LayoutParams.WRAP_CONTENT
        popup.width = WindowManager.LayoutParams.WRAP_CONTENT
        // Closes the popup window when touch outside of it - when looses focus
        popup.isOutsideTouchable = true
        popup.isFocusable = true

        popupLayoutBinding.deleteAll.setOnClickListener {
            if (messageList.isNotEmpty())
                thread.start()
            else {
                toast("Already chat is cleared")
                if (popup != null)
                    popup.dismiss()
            }
        }
        // Show anchored to button
        popup.setBackgroundDrawable(BitmapDrawable())

        popup.showAtLocation(popupLayoutBinding.root, Gravity.BOTTOM or Gravity.START, 40, 200)
        popup.update(40, 200, WindowManager.LayoutParams.WRAP_CONTENT, (700 / 5))
        popup.animationStyle = R.anim.item_animation_fall_down;
//        popup.showAsDropDown(anchorView)
    }

    private fun renderMessages(messages: List<Message>?) {
        messageList.clear()
        messageList.addAll(messages!!)
        chatAdapter.notifyDataSetChanged()
        binding.recyclerView.adapter = chatAdapter
    }

    var thread: Thread = object : Thread() {
        override fun run() {
            messageViewModel!!.deleteAll()
            runOnUiThread {
                deleteAll()
            }
        }
    }

    private fun deleteAll() {
        popup.dismiss()
        chatAdapter.notifyDataSetChanged()
        toast("your chat is clear")
    }

}