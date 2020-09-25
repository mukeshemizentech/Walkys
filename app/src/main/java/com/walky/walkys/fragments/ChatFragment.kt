package com.walky.walkys.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.walky.walkys.adapters.UserListAdapter
import com.walky.responses.HomeResponse
import com.walky.walkys.R
import com.walky.walkys.activities.CreateGroupActivity
import com.walky.walkys.activities.NewPostActivity
import com.walky.walkys.databinding.FragmentChatBinding
import java.util.*


class ChatFragment : Fragment() {

    private var adapter: UserListAdapter? = null
    private val arrayList: ArrayList<HomeResponse> = ArrayList<HomeResponse>()
    lateinit var binding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        binding.fabIconGroup.setOnClickListener { view ->
            startActivity(
                Intent(requireContext(), CreateGroupActivity::class.java)
            )
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        adapter = UserListAdapter(requireContext(), arrayList)
        binding.recyclerView.adapter = adapter

        return binding.root
    }

}