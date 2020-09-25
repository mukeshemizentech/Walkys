package com.walky.walker.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.walky.responses.HomeResponse
import com.walky.walker.adapters.MyRequestAdaper
import com.walky.walkys.R
import com.walky.walkys.databinding.ActivityMyRequestBinding
import java.util.*

class MyRequestActivity : AppCompatActivity() {

    private var adaper: MyRequestAdaper? = null
    lateinit var binding: ActivityMyRequestBinding
    private val arrayList: ArrayList<HomeResponse> = ArrayList<HomeResponse>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_request)
        binding.headerCommon.backIv.setOnClickListener { view -> onBackPressed() }
        binding.headerCommon.titleTv.setText("My Request")
        binding.recyclerView.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        adaper = MyRequestAdaper(this, arrayList)
        binding.recyclerView.setAdapter(adaper)
    }
}