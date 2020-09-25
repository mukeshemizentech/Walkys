package com.walky.utils

import android.app.Application
import android.util.Log
//import com.github.hiteshsondhi88.libffmpeg.FFmpeg
//import com.github.hiteshsondhi88.libffmpeg.FFmpegLoadBinaryResponseHandler
//import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException
import com.pixplicity.easyprefs.library.Prefs

class PrefsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize the Prefs class
        Prefs.Builder()
            .setContext(this)
            .setMode(MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        //Load FFMpeg library
//        try {
//            FFmpeg.getInstance(this).loadBinary(object : FFmpegLoadBinaryResponseHandler {
//                override fun onFailure() {
//                    Log.e("FFMpeg", "Failed to load FFMpeg library.")
//                }
//
//                override fun onSuccess() {
//                    Log.i("FFMpeg", "FFMpeg Library loaded!")
//                }
//
//                override fun onStart() {
//                    Log.i("FFMpeg", "FFMpeg Started")
//                }
//
//                override fun onFinish() {
//                    Log.i("FFMpeg", "FFMpeg Stopped")
//                }
//            })
//        } catch (e: FFmpegNotSupportedException) {
//            e.printStackTrace()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }
}