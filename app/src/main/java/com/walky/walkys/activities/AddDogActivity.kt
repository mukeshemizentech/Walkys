package com.walky.walkys.activities

import android.Manifest
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
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
import com.walky.walkys.R
import com.walky.walkys.adapters.SelectImageAdapter
import com.walky.walkys.databinding.ActivityAddDogBinding
import java.util.ArrayList

class AddDogActivity : AppCompatActivity() {

    private val REQUEST_CAMERA_PERMISSION = 1
    var imageUri: Uri? = null
    private var uploadImageArray = ArrayList<Uri>()
    var selectImageAdapter: SelectImageAdapter? = null
    lateinit var binding: ActivityAddDogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_dog)

        binding.header.titleTv.text = "Add Dog"
        binding.header.homeMenu.setImageResource(R.drawable.back)
        Utils.tintColor(this, binding.header.homeMenu, R.color.white)
        binding.header.homeMenu.setOnClickListener { finish() }
        binding.header.optionMenu.visibility = View.VISIBLE
        binding.header.optionMenu.setImageResource(R.drawable.add_post)
        binding.header.optionMenu.setOnClickListener {
            startActivity(
                Intent(
                    this@AddDogActivity,
                    NewPostActivity::class.java
                )
            )
        }

        binding.selectImageRV.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        selectImageAdapter = SelectImageAdapter(this, uploadImageArray)
        binding.selectImageRV.adapter = selectImageAdapter
        selectImageAdapter!!.notifyDataSetChanged()

        binding.uploadCons.setOnClickListener {
            ImagePopup()
        }

    }

    private fun ImagePopup() {
        try {
            val dialog = Dialog(this@AddDogActivity)
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
                            this@AddDogActivity,
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this@AddDogActivity,
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
                                this@AddDogActivity,
                                Manifest.permission.CAMERA
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            ActivityCompat.requestPermissions(
                                this@AddDogActivity,
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
        }
//        else if (requestCode == 300 && resultCode == RESULT_OK && data != null) {//---------------Video--------------
//            imageUri = data?.data
//
//            TrimVideo.activity(imageUri.toString())
////        .setCompressOption(new CompressOption()) //empty constructor for default compress option
//                .setDestination("/storage/emulated/0/DCIM/walkys")  //
//                .setTrimType(TrimType.MIN_MAX_DURATION)
//                .setMinToMax(10, 30)  /// default output path /storage/emulated/0/DOWNLOADS
//                .start(this)
//
////            uploadImageArray.add(imageUri!!)
////            selectImageAdapter = SelectImageAdapter(this, uploadImageArray)
////            binding.selectImageRV.adapter = selectImageAdapter
////            selectImageAdapter!!.notifyDataSetChanged()
//        } else if (requestCode == 400 && resultCode == RESULT_OK && data != null) {//---------------Video--------------
//            imageUri = data.data
////            executeCutVideoCommand(10 * 1000, 30 * 1000, imageUri!!)
////            uploadImageArray.add(imageUri!!)
////            selectImageAdapter = SelectImageAdapter(this, uploadImageArray)
////            binding.selectImageRV.adapter = selectImageAdapter
////            selectImageAdapter!!.notifyDataSetChanged()
//
//
//            TrimVideo.activity(imageUri.toString())
////        .setCompressOption(new CompressOption()) //empty constructor for default compress option
//                .setDestination("/storage/emulated/0/DCIM/walkys")
//                .setHideSeekBar(false)
//                .setMinToMax(10, 30)//default output path /storage/emulated/0/DOWNLOADS
//                .start(this)
//
//        }
//        if (requestCode === TrimVideo.VIDEO_TRIMMER_REQ_CODE && data != null) {
//            val uri = Uri.parse(TrimVideo.getTrimmedVideoPath(data))
//            Log.d("TAG", "Trimmed path:: $uri")
//
//            uploadImageArray.add(uri!!)
//            selectImageAdapter = SelectImageAdapter(this, uploadImageArray)
//            binding.selectImageRV.adapter = selectImageAdapter
//            selectImageAdapter!!.notifyDataSetChanged()
////            trimVideo(10*1000,30*1000, uri.toString())
//        }
    }
}