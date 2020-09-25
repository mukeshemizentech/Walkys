package com.walky.walkys.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.walky.responses.HomeResponse
import com.walky.walkys.R
import com.walky.walkys.activities.FollowAndFollowingActivity
import com.walky.walkys.adapters.DogListAdapter
import com.walky.walkys.adapters.ViewPageTabAdapter
import com.walky.walkys.databinding.FragmentMyProfileBinding
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var viewPage_tabAdapter: ViewPageTabAdapter? = null
    private var adapter: DogListAdapter? = null
    private val arrayList: ArrayList<HomeResponse> = ArrayList<HomeResponse>()
    lateinit var binding: FragmentMyProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile, container, false)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        adapter = DogListAdapter(requireContext(), arrayList, "myProfileFragment")
        binding.recyclerView.adapter = adapter

//        //Image Progress bar Code
//        val params = binding.progressBarIv!!.layoutParams as PercentFrameLayout.LayoutParams
//        val info = params.percentLayoutInfo
//        info.widthPercent = 0.90f
//        info.heightPercent = 1.0f
////        binding.progressBarIv.requestLayout();
//        binding.progressBarIv.layoutParams = params;

        binding.followersCountTv.setOnClickListener {
            startActivity(Intent(requireContext(), FollowAndFollowingActivity::class.java))
        }
        binding.followingCountTv.setOnClickListener{
            startActivity(Intent(requireContext(), FollowAndFollowingActivity::class.java))
        }

        addControls()

        return binding.root

    }
    private fun getWidthOfView(contentview: View):Int {
        contentview.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        //contentview.getMeasuredWidth();
        return contentview.measuredHeight
    }

    private fun addControls() {
        viewPage_tabAdapter = ViewPageTabAdapter(childFragmentManager)
        viewPage_tabAdapter!!.addFragment(AdventureFragment(), getString(R.string.adventure))
        viewPage_tabAdapter!!.addFragment(ImageFragment(), getString(R.string.images))
        viewPage_tabAdapter!!.addFragment(LeaderBoardFragment(), getString(R.string.leaderboard))
        binding.userProfileViewPager.adapter = viewPage_tabAdapter
        binding.userProfileTabLayout.setupWithViewPager(binding.userProfileViewPager)
        binding.userProfileViewPager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                binding.userProfileTabLayout
            )
        )
        binding.userProfileTabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.userProfileViewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}