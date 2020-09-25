package com.walky.walkys.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.walky.responses.HomeResponse
import com.walky.walkys.R
import com.walky.walkys.adapters.LeaderBoardAdapter
import com.walky.walkys.databinding.FragmentLeaderBoardBinding
import java.util.*


class LeaderBoardFragment : Fragment() {

    private var adapter: LeaderBoardAdapter? = null
    private val arrayList: ArrayList<HomeResponse> = ArrayList<HomeResponse>()
    lateinit var binding: FragmentLeaderBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_leader_board, container, false)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//        binding.recyclerView.isNestedScrollingEnabled = false
        adapter = LeaderBoardAdapter(requireContext(), arrayList)
        binding.recyclerView.adapter = adapter

        return binding.getRoot()
    }
}