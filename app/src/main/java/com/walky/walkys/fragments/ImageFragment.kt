package com.walky.walkys.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.walky.responses.HomeResponse
import com.walky.walkys.R
import com.walky.walkys.adapters.ImageAdapter
import com.walky.walkys.databinding.FragmentImageBinding
import java.util.*

class ImageFragment : Fragment() {

    private lateinit var adapter: ImageAdapter
    private val arrayList: ArrayList<HomeResponse> = ArrayList<HomeResponse>()
    lateinit var binding: FragmentImageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image, container, false)


        binding.recyclerView.layoutManager = GridLayoutManager(activity, 3)
//        binding.recyclerView.isNestedScrollingEnabled = false
        adapter = ImageAdapter(requireContext(), arrayList)
        binding.recyclerView.adapter = adapter

        return binding.root
    }

}