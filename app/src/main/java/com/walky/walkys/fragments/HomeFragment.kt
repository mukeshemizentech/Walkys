package com.walky.walkys.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.walky.responses.HomeResponse
import com.walky.utils.drawableBackColor
import com.walky.walkys.adapters.HomeAdaper
import com.walky.walkys.R
import com.walky.walkys.activities.FollowingListActivity
import com.walky.walkys.databinding.FragmentHomeBinding
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment : Fragment() {

    fun HomeFragment() {
        // Required empty public constructor
    }

    var adaper: HomeAdaper? = null
    var arrayList: ArrayList<HomeResponse> = ArrayList<HomeResponse>()
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        adaper = context?.let { HomeAdaper(it, arrayList) }
        binding.recyclerView.adapter = adaper

        requireContext().drawableBackColor(binding.followEmpty.followMoreBtn, resources.getColor(R.color.colorPrimary))
        binding.followingTv.setOnClickListener {

            binding.followLL.visibility = VISIBLE
            binding.recyclerView.visibility = GONE
   /*         val layoutParams: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)
            binding.followLL.layoutParams = layoutParams
*/
            binding.followingTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.followingView.visibility = VISIBLE
            binding.popularTv.setTextColor(resources.getColor(R.color.textColorSecondary))
            binding.popularView.visibility = GONE
        }

        binding.popularTv.setOnClickListener {
            binding.followLL.visibility = GONE
            binding.recyclerView.visibility = VISIBLE
            binding.followingTv.setTextColor(resources.getColor(R.color.textColorSecondary))
            binding.followingView.visibility = GONE
            binding.popularTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.popularView.visibility = VISIBLE
        }

        binding.followEmpty.followMoreBtn.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    FollowingListActivity::class.java
                )
            )
        }

        return binding.getRoot()
    }

}