package com.walky.walkys.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.walky.utils.getMimeType
import com.walky.walkys.R
import com.walky.walkys.databinding.SelectImageItemBinding
import java.util.*


class SelectImageAdapter(var context: Context, var arrayListUri: ArrayList<Uri>) :
    RecyclerView.Adapter<SelectImageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SelectImageItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.select_image_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("sadfafsaddf", arrayListUri[position].toString() + "")

        holder.binding.deleteImage.setOnClickListener {

            //                if (arrayListString.get(position).getId()!=null){
            ////                    raw_post = i;
            ////                    DeleteIamgeMethod(i);
            //                }else {
            //                    for (int k =0;k<arrayListUri.size();k++){
            //                        arrayListUri.remove(k);
            //                    }

            arrayListUri.removeAt(position)
            notifyItemRemoved(position)
            notifyItemChanged(position)
            notifyItemRangeChanged(position, arrayListUri.size)
            //                }
        }


        val extension: String = getMimeType(context, arrayListUri[position])!!
        Log.d("extension_type", extension)
        if (extension == "mp4") {
            holder.binding.playIv.visibility = View.VISIBLE

//            val frames = ArrayList<Bitmap>()
//            val retriever = MediaMetadataRetriever()
//            try {
//                //path of the video of which you want frames
//                retriever.setDataSource(arrayListUri[position].toString())
//            } catch (e: Exception) {
//                println("Exception= $e")
//            }
//            val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
//            val duration_millisec = duration!!.toInt() //duration in millisec
//
//            val duration_second = duration_millisec / 1000 //millisec to sec.
//            Log.d("duration_", duration_second.toString())
//            val frames_per_second = 2 //no. of frames want to retrieve per second
//
//            var numeroFrameCaptured = frames_per_second * duration_second
//
//            Log.d("frames_size",frames.size.toString())
//            var bitmap: Bitmap?=null
//                var i = 0
//                while (i < 100) {
////                    val frameTime: Long = maxDur * i / 100
//                    val bitmap: Bitmap? = retriever.getFrameAtTime(
//                        5*1000000, //5 seconds  1sec = 1000000
//                        MediaMetadataRetriever.OPTION_CLOSEST_SYNC
//                    )
//                    frames.add(bitmap!!)
//                    i += 10
//                }
//            Log.d("frames_size",frames.size.toString())
//            val times = System.currentTimeMillis()
//
//            holder.binding.selectImage.setImageBitmap(bitmap)


            Glide.with(context)
                .load(arrayListUri[position])
                .centerCrop()
                .thumbnail(Glide.with(context).load(arrayListUri[position]))
                .into(holder.binding.selectImage)

        } else {
            holder.binding.playIv.visibility = View.GONE
            Glide.with(context)
                .load(arrayListUri[position])
                .centerCrop()
                .thumbnail(Glide.with(context).load(arrayListUri[position]))
                .into(holder.binding.selectImage)
        }

        holder.binding.selectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, arrayListUri[position])
            if (extension == "mp4")
                intent.setDataAndType(arrayListUri[position], "video/*")
            else
                intent.setDataAndType(arrayListUri[position], "image/*")

            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return arrayListUri.size
    }

    inner class ViewHolder(itemView: SelectImageItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var binding: SelectImageItemBinding = itemView
    }

    fun remove(position: Int, data: Uri) {
        arrayListUri.remove(data)
        notifyItemChanged(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, arrayListUri.size)
    }


}


