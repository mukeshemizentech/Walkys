package com.walky.walkys.fragments

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.walky.walkys.R
import com.walky.walkys.adapters.SearchAdapter
import com.walky.walkys.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {


    lateinit var binding: FragmentSearchBinding;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        binding.nearYouLL.setBackgroundResource(R.drawable.button_primary_rect)
        binding.cafeLL.setBackgroundResource(R.drawable.border_primary)
        binding.beachesLL.setBackgroundResource(R.drawable.border_primary)
        binding.parkLL.setBackgroundResource(R.drawable.border_primary)
        binding.userLL.setBackgroundResource(R.drawable.border_primary)
        binding.petLL.setBackgroundResource(R.drawable.border_primary)

        binding.nearYouTv.setTextColor(resources.getColor(R.color.white))
        binding.cafeTv.setTextColor(resources.getColor(R.color.colorPrimary))
        binding.beachesTv.setTextColor(resources.getColor(R.color.colorPrimary))
        binding.parksTv.setTextColor(resources.getColor(R.color.colorPrimary))
        binding.userTv.setTextColor(resources.getColor(R.color.colorPrimary))
        binding.petTv.setTextColor(resources.getColor(R.color.colorPrimary))

        binding.nearYouLL.setOnClickListener {
            itemInCenter(0)
            binding.nearYouLL.setBackgroundResource(R.drawable.button_primary_rect)
            binding.cafeLL.setBackgroundResource(R.drawable.border_primary)
            binding.beachesLL.setBackgroundResource(R.drawable.border_primary)
            binding.parkLL.setBackgroundResource(R.drawable.border_primary)
            binding.userLL.setBackgroundResource(R.drawable.border_primary)
            binding.petLL.setBackgroundResource(R.drawable.border_primary)

            binding.nearYouTv.setTextColor(resources.getColor(R.color.white))
            binding.cafeTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.beachesTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.parksTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.userTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.petTv.setTextColor(resources.getColor(R.color.colorPrimary))


        }

        binding.cafeLL.setOnClickListener {
            itemInCenter(0)
            binding.nearYouLL.setBackgroundResource(R.drawable.border_primary)
            binding.cafeLL.setBackgroundResource(R.drawable.button_primary_rect)
            binding.beachesLL.setBackgroundResource(R.drawable.border_primary)
            binding.parkLL.setBackgroundResource(R.drawable.border_primary)
            binding.userLL.setBackgroundResource(R.drawable.border_primary)
            binding.petLL.setBackgroundResource(R.drawable.border_primary)


            binding.nearYouTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.cafeTv.setTextColor(resources.getColor(R.color.white))
            binding.beachesTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.parksTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.userTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.petTv.setTextColor(resources.getColor(R.color.colorPrimary))


        }
        binding.beachesLL.setOnClickListener {
            itemInCenter(1)
            binding.nearYouLL.setBackgroundResource(R.drawable.border_primary)
            binding.cafeLL.setBackgroundResource(R.drawable.border_primary)
            binding.beachesLL.setBackgroundResource(R.drawable.button_primary_rect)
            binding.parkLL.setBackgroundResource(R.drawable.border_primary)
            binding.userLL.setBackgroundResource(R.drawable.border_primary)
            binding.petLL.setBackgroundResource(R.drawable.border_primary)

            binding.nearYouTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.cafeTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.beachesTv.setTextColor(resources.getColor(R.color.white))
            binding.parksTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.userTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.petTv.setTextColor(resources.getColor(R.color.colorPrimary))
        }

        binding.parkLL.setOnClickListener {
            itemInCenter(2)
            binding.nearYouLL.setBackgroundResource(R.drawable.border_primary)
            binding.cafeLL.setBackgroundResource(R.drawable.border_primary)
            binding.beachesLL.setBackgroundResource(R.drawable.border_primary)
            binding.parkLL.setBackgroundResource(R.drawable.button_primary_rect)
            binding.userLL.setBackgroundResource(R.drawable.border_primary)
            binding.petLL.setBackgroundResource(R.drawable.border_primary)

            binding.nearYouTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.cafeTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.beachesTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.parksTv.setTextColor(resources.getColor(R.color.white))
            binding.userTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.petTv.setTextColor(resources.getColor(R.color.colorPrimary))
        }
        binding.userLL.setOnClickListener {
            itemInCenter(3)
            binding.nearYouLL.setBackgroundResource(R.drawable.border_primary)
            binding.cafeLL.setBackgroundResource(R.drawable.border_primary)
            binding.beachesLL.setBackgroundResource(R.drawable.border_primary)
            binding.parkLL.setBackgroundResource(R.drawable.border_primary)
            binding.userLL.setBackgroundResource(R.drawable.button_primary_rect)
            binding.petLL.setBackgroundResource(R.drawable.border_primary)

            binding.nearYouTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.cafeTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.beachesTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.parksTv.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.userTv.setTextColor(resources.getColor(R.color.white))
            binding.petTv.setTextColor(resources.getColor(R.color.colorPrimary))
        }
        binding.petLL.setOnClickListener {
            itemInCenter(4)
            with(binding) {
                nearYouLL.setBackgroundResource(R.drawable.border_primary)
                cafeLL.setBackgroundResource(R.drawable.border_primary)
                beachesLL.setBackgroundResource(R.drawable.border_primary)
                parkLL.setBackgroundResource(R.drawable.border_primary)
                userLL.setBackgroundResource(R.drawable.border_primary)
                petLL.setBackgroundResource(R.drawable.button_primary_rect)

                nearYouTv.setTextColor(resources.getColor(R.color.colorPrimary))
                cafeTv.setTextColor(resources.getColor(R.color.colorPrimary))
                beachesTv.setTextColor(resources.getColor(R.color.colorPrimary))
                parksTv.setTextColor(resources.getColor(R.color.colorPrimary))
                userTv.setTextColor(resources.getColor(R.color.colorPrimary))
                petTv.setTextColor(resources.getColor(R.color.white))
            }
            
        }



        addRecycleView()
        return binding.getRoot()
    }

    private fun itemInCenter(index: Int) {
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        val viewWidth: Int =
            binding.horizontalView.width + binding.horizontalView.paddingLeft + binding.horizontalView.paddingRight
        val scrollX: Int = viewWidth * index - (screenWidth / 2 + viewWidth / 1)
        binding.horizontalView.smoothScrollTo(scrollX, 0)
    }
//    private fun animateGravity(toGravity: Int) {
//        val layoutTransition: LayoutTransition = binding.container.getLayoutTransition()
//        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
//        layoutTransition.setDuration(500)
//        layoutTransition.setInterpolator(
//            LayoutTransition.CHANGING,
//            AccelerateDecelerateInterpolator()
//        )
//        val layoutParams = binding.blueOval.getLayoutParams() as FrameLayout.LayoutParams
//        layoutParams.gravity = toGravity
//        binding.blueOval.setLayoutParams(layoutParams)
//    }

    private fun addRecycleView() {
        val searchAdapter = SearchAdapter(requireContext())
        binding.searchRecycleFirst.setLayoutManager(LinearLayoutManager(requireContext()))
        binding.searchRecycleFirst.setAdapter(searchAdapter)
    }

}