package com.walky.walker.activities

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.walky.utils.Utils
import com.walky.walkys.R
import com.walky.walkys.databinding.ActivityUpComingBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class UpComingActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpComingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_up_coming)
        binding.headerCommon.titleTv.setText("Up coming request")
        binding.headerCommon.filterIv.setVisibility(View.GONE)
        binding.headerCommon.backIv.setOnClickListener { view -> onBackPressed() }
        Log.d("Current_time", Utils.currentTime("HH:mm:ss")!!)
        val format = SimpleDateFormat("HH:mm:ss")
        var startDate: Date? = null
        try {
            startDate = format.parse(Utils.currentTime("HH:mm:ss"))
        } catch (e: ParseException) {
            e.printStackTrace()
        }

//2. feed it to a Calendar Object
        val calendar = Calendar.getInstance()
        calendar.time = startDate

//3. get the hour, minute, second variable
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        val second = calendar[Calendar.SECOND]

//4. apply to your Chronometer class.
        binding.chronometer.setBase(SystemClock.elapsedRealtime() - (hour * 60000 * 60 + minute * 60000 + second * 1000))

//        binding.chronometer.setText(2*1000);
        binding.chronometer.start()
        binding.acceptTv.setOnClickListener { view ->
            binding.headerCommon.titleTv.setText(R.string.my_booking)
            binding.rejectTv.setVisibility(View.GONE)
            binding.acceptTv.setText(R.string.start)
            binding.cons4.setVisibility(View.GONE)
        }
        binding.rejectTv.setOnClickListener { view ->
            binding.headerCommon.titleTv.setText(R.string.my_booking)
            binding.acceptTv.setVisibility(View.GONE)
            binding.rejectTv.setText(R.string.stop)
            binding.cons4.setVisibility(View.VISIBLE)
        }
    }
}