package com.walky.walkys.activities

//import com.gowtham.library.utils.TrimType
//import com.gowtham.library.utils.TrimVideo
import android.Manifest
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.gowtham.library.utils.TrimType
import com.gowtham.library.utils.TrimVideo
import com.walky.utils.Utils
import com.walky.utils.drawableBackColor
import com.walky.walkys.R
import com.walky.walkys.adapters.SelectImageAdapter
import com.walky.walkys.databinding.ActivityNewPostBinding
import java.io.File
import java.util.*


class NewPostActivity : AppCompatActivity() {

    private val REQUEST_CAMERA_PERMISSION = 1
    var imageUri: Uri? = null
    private var uploadImageArray = ArrayList<Uri>()
    var selectImageAdapter: SelectImageAdapter? = null

    lateinit var binding: ActivityNewPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_post)

        binding.header.titleTv.text = resources.getString(R.string.post_image_video)
        binding.header.optionMenu.visibility = View.INVISIBLE
        binding.header.homeMenu.setImageResource(R.drawable.back)
        Utils.tintColor(this, binding.header.homeMenu, R.color.white)
        binding.header.homeMenu.setOnClickListener { view -> finish() }
        drawableBackColor(binding.postBtn, resources.getColor(R.color.colorPrimary))
        binding.postBtn.setOnClickListener {
            startActivity(
                Intent(
                    this@NewPostActivity,
                    SuccessActivity::class.java
                )
            )
        }
        binding.selectImageRV.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        selectImageAdapter = SelectImageAdapter(this, uploadImageArray)
        binding.selectImageRV.adapter = selectImageAdapter
        selectImageAdapter!!.notifyDataSetChanged()
        binding.addImageLL.setOnClickListener {
            ImagePopup()
        }

        binding.addVideotLL.setOnClickListener {
            VideoPopup()
        }
    }


    private fun ImagePopup() {
        try {
            val dialog = Dialog(this@NewPostActivity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.decorView.setBackgroundResource(android.R.color.transparent)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.pop_profile)
            dialog.show()
            val txtGallery = dialog.findViewById<View>(R.id.layoutGallery) as LinearLayout
            val txtCamera = dialog.findViewById<View>(R.id.layoutCamera) as LinearLayout
            txtCamera.setOnClickListener { v: View? ->
                val currentAPIVersion = Build.VERSION.SDK_INT
                if (currentAPIVersion >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(
                            this@NewPostActivity,
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this@NewPostActivity,
                            arrayOf(
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ),
                            REQUEST_CAMERA_PERMISSION
                        )
                    } else {
                        selectCameraImage()
                        dialog.dismiss()
                    }
                } else {
                    selectCameraImage()
                    dialog.dismiss()
                }
            }
            txtGallery.setOnClickListener { v: View? ->
                val currentAPIVersion = Build.VERSION.SDK_INT
                if (currentAPIVersion >= Build.VERSION_CODES.M) {
                    arrayOf(
                        if (ActivityCompat.checkSelfPermission(
                                this@NewPostActivity,
                                Manifest.permission.CAMERA
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            ActivityCompat.requestPermissions(
                                this@NewPostActivity,
                                arrayOf(
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                ),
                                2
                            )
                        } else {
                            dialog.dismiss()
                            val intent =
                                Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                )
                            intent.type = "image/*"
//                            intent.type = "*/*";
                            intent.action = Intent.ACTION_PICK
                            startActivityForResult(
                                Intent.createChooser(intent, "Select Image"),
                                100
                            )
                        }
                    )

                } else {
                    dialog.dismiss()
                    val intent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    intent.type = "image/*"
//                    intent.type = "*/*";
                    intent.action = Intent.ACTION_PICK
                    startActivityForResult(Intent.createChooser(intent, "Select Image"), 100)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun VideoPopup() {
        try {
            val dialog = Dialog(this@NewPostActivity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.decorView.setBackgroundResource(android.R.color.transparent)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.pop_profile)
            dialog.show()
            val txtGallery = dialog.findViewById<View>(R.id.layoutGallery) as LinearLayout
            val txtCamera = dialog.findViewById<View>(R.id.layoutCamera) as LinearLayout
            txtCamera.setOnClickListener { v: View? ->
                val currentAPIVersion = Build.VERSION.SDK_INT
                if (currentAPIVersion >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(
                            this@NewPostActivity,
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this@NewPostActivity,
                            arrayOf(
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ),
                            REQUEST_CAMERA_PERMISSION
                        )
                    } else {
                        selectCameraVideo()
                        dialog.dismiss()
                    }
                } else {
                    selectCameraVideo()
                    dialog.dismiss()
                }
            }
            txtGallery.setOnClickListener { v: View? ->
                val currentAPIVersion = Build.VERSION.SDK_INT
                if (currentAPIVersion >= Build.VERSION_CODES.M) {
                    arrayOf(
                        if (ActivityCompat.checkSelfPermission(
                                this@NewPostActivity,
                                Manifest.permission.CAMERA
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            ActivityCompat.requestPermissions(
                                this@NewPostActivity,
                                arrayOf(
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                ),
                                2
                            )
                        } else {
                            dialog.dismiss()
                            val intent =
                                Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                )
                            intent.type = "video/*"
//                            intent.type = "*/*";
                            intent.action = Intent.ACTION_PICK
                            startActivityForResult(
                                Intent.createChooser(intent, "Select Video"),
                                400
                            )
                        }
                    )

                } else {
                    dialog.dismiss()
                    val intent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    intent.type = "video/*"
//                    intent.type = "*/*";
                    intent.action = Intent.ACTION_PICK
                    startActivityForResult(Intent.createChooser(intent, "Select Video"), 400)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun selectCameraImage() {
        // TODO Auto-generated method stub
        val fileName = "new-photo-name.jpg"
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, fileName)
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image captured by camera")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1)
        startActivityForResult(intent, 200)
    }

    private fun selectCameraVideo() {
        // TODO Auto-generated method stub
//        val fileName = "myVideo.mp4"
//        val values = ContentValues()
//        values.put(MediaStore.Images.Media.TITLE, fileName)
//        values.put(MediaStore.Images.Media.DESCRIPTION, "Video captured by camera")
//        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
//        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1)
        startActivityForResult(intent, 300)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            imageUri = data.data
            uploadImageArray.add(imageUri!!)
            selectImageAdapter = SelectImageAdapter(this, uploadImageArray)
            binding.selectImageRV.adapter = selectImageAdapter
            selectImageAdapter!!.notifyDataSetChanged()
        } else if (requestCode == 200) {
            uploadImageArray.add(imageUri!!)
            selectImageAdapter = SelectImageAdapter(this, uploadImageArray)
            binding.selectImageRV.adapter = selectImageAdapter
            selectImageAdapter!!.notifyDataSetChanged()
        } else if (requestCode == 300 && resultCode == RESULT_OK && data != null) {//---------------Video--------------
            imageUri = data?.data

            TrimVideo.activity(imageUri.toString())
//        .setCompressOption(new CompressOption()) //empty constructor for default compress option
                .setDestination("/storage/emulated/0/DCIM/walkys")  //
                .setTrimType(TrimType.MIN_MAX_DURATION)
                .setMinToMax(10, 30)  /// default output path /storage/emulated/0/DOWNLOADS
                .start(this)

//            uploadImageArray.add(imageUri!!)
//            selectImageAdapter = SelectImageAdapter(this, uploadImageArray)
//            binding.selectImageRV.adapter = selectImageAdapter
//            selectImageAdapter!!.notifyDataSetChanged()
        } else if (requestCode == 400 && resultCode == RESULT_OK && data != null) {//---------------Video--------------
            imageUri = data.data
//            executeCutVideoCommand(10 * 1000, 30 * 1000, imageUri!!)
//            uploadImageArray.add(imageUri!!)
//            selectImageAdapter = SelectImageAdapter(this, uploadImageArray)
//            binding.selectImageRV.adapter = selectImageAdapter
//            selectImageAdapter!!.notifyDataSetChanged()


            TrimVideo.activity(imageUri.toString())
//        .setCompressOption(new CompressOption()) //empty constructor for default compress option
                .setDestination("/storage/emulated/0/DCIM/walkys")
                .setHideSeekBar(false)
                .setMinToMax(10, 30)//default output path /storage/emulated/0/DOWNLOADS
                .start(this)

        }
        if (requestCode === TrimVideo.VIDEO_TRIMMER_REQ_CODE && data != null) {
            val uri = Uri.parse(TrimVideo.getTrimmedVideoPath(data))
            Log.d("TAG", "Trimmed path:: $uri")

            uploadImageArray.add(uri!!)
            selectImageAdapter = SelectImageAdapter(this, uploadImageArray)
            binding.selectImageRV.adapter = selectImageAdapter
            selectImageAdapter!!.notifyDataSetChanged()
//            trimVideo(10*1000,30*1000, uri.toString())
        }
    }

    var path: String? = null
    var orignalPath: String? = null
    var des: File? = null
    var command = arrayOf(String())
    var mConnection: ServiceConnection? = null

//    var ffMpegService : FFMpegService?=null
//    private fun trimVideo(start: Int, end: Int, stringPath: String){
//
//        val folder = File("" + Environment.getExternalStorageDirectory() + "/TrimVideos")
//        if (!folder.exists()){
//            folder.mkdir()
//        }
//
//        path = stringPath
//        val fileExt = ".mp4"
//        des = File(folder, path + fileExt)
//        orignalPath = getFilePath(this, imageUri!!)
//
//        Log.d("orignalPath",orignalPath.toString())
//        Log.d("destinationPath",des!!.absolutePath.toString())
//        val duration = 10
//
//        command = arrayOf(
//            arrayOf(
//                "-ss",
//                "" + start / 1000,
//                "-y",
//                "-i",
//                orignalPath,
//                "-t",
//                "" + (end - start) / 1000,
//                "-vcodec",
//                "mpeg4",
//                "-b:v",
//                "2097152",
//                "-b:a",
//                "48000",
//                "-ac",
//                "2",
//                "-ar",
//                "22050",
//                des!!.absolutePath
//            ).toString()
//        )
//
//        val intent = Intent(this@NewPostActivity, FFMpegService::class.java)
//        intent.putExtra("duration", duration.toString())
//        intent.putExtra("command", command)
//        intent.putExtra("destination", des!!.absolutePath)
//        startService(intent)
//
//        mConnection = object : ServiceConnection {
//            override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {
//                val binder : FFMpegService.LocalBinder = iBinder as FFMpegService.LocalBinder
//                ffMpegService = binder.getServiceInstance()
//                ffMpegService!!.registerClient(this@NewPostActivity)
//
////                var observer = object : Observer<Int>() {
////                    fun onChanged(@Nullable integer: Int) {
////                        CircularProgressbar.visibility = View.VISIBLE
////                    }
////                }
////
////                ffMpegService!!.getPercentage().observe(this@NewPostActivity,observer)
//
//
//            }
//
//            override fun onServiceDisconnected(name: ComponentName?) {
//
//            }
//
//        }
//
//        bindService(intent, mConnection as ServiceConnection,Context.BIND_AUTO_CREATE)
//
//
//    }

    //

//    val TAG = "NewPostActivity"
//    open fun executeCutVideoCommand(startMs: Int, endMs: Int, selectedVideoUri: Uri) {
//        val moviesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
//        val filePrefix = "cut_video"
//        val fileExtn = ".mp4"
//        val yourRealPath: String = getFilePath(this, selectedVideoUri)!!
//        var dest = File(moviesDir, filePrefix + fileExtn)
//
//        val p = Runtime.getRuntime().exec(arrayOf("bash", "-c", "ls /home/XXX"))
//
//        var fileNo = 0
//        while (dest.exists()) {
//            fileNo++
//            dest = File(moviesDir, filePrefix + fileNo + fileExtn)
//        }
//        Log.d(TAG, "startTrim: src: $yourRealPath")
//        Log.d(TAG, "startTrim: dest: " + dest.absolutePath)
//        Log.d(TAG, "startTrim: startMs: $startMs")
//        Log.d(TAG, "startTrim: endMs: $endMs")
////        filePath = dest.absolutePath
//        //String[] complexCommand = {"-i", yourRealPath, "-ss", "" + startMs / 1000, "-t", "" + endMs / 1000, dest.getAbsolutePath()};
//        val complexCommand = arrayOf(
//            "-ss",
//            "" + startMs / 1000,
//            "-y",
//            "-i",
//            yourRealPath,
//            "-t",
//            "" + (endMs - startMs) / 1000,
//            "-vcodec",
//            "mpeg4",
//            "-b:v",
//            "2097152",
//            "-b:a",
//            "48000",
//            "-ac",
//            "2",
//            "-ar",
//            "22050",
//            dest.absolutePath
//        )
//        execFFmpegBinary(complexCommand)
//    }
//
//    var ffmpeg: FFmpeg? = null
//
//
//
//    private fun execFFmpegBinary(command: Array<String>) {
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
//    }
}