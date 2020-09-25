package com.walky.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.walky.walkys.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class Utils {

    companion object {

        fun getConvertedFile(folder: String, fileName: String): File {
            val f = File(folder)

            if (!f.exists())
                f.mkdirs()

            return File(f.path + File.separator + fileName)
        }

        fun refreshGallery(path: String, context: Context) {

            val file = File(path)
            try {
                val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                val contentUri = Uri.fromFile(file)
                mediaScanIntent.data = contentUri
                context.sendBroadcast(mediaScanIntent)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun milliSecondsToTimer(milliseconds: Long): String {
            var finalTimerString = ""
            var secondsString = ""

            // Convert total duration into time
            val hours = (milliseconds / (1000 * 60 * 60)).toInt()
            val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
            val seconds = (milliseconds % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()
            // Add hours if there
            if (hours > 0) {
                finalTimerString = hours.toString() + ":"
            }

            // Prepending 0 to seconds if it is one digit
            if (seconds < 10) {
                secondsString = "0" + seconds
            } else {
                secondsString = "" + seconds
            }

            finalTimerString = finalTimerString + minutes + ":" + secondsString

            // return timer string
            return finalTimerString
        }

        private var retryCustomAlert: AlertDialog? = null
        var isShown = false
        var progressDialog: AlertDialog? = null

        fun statusBarHideShow(activity: Activity) {
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        fun getColoredString(mString: String?, colorId: Int): Spannable? {
            val spannable: Spannable = SpannableString(mString)
            spannable.setSpan(
                ForegroundColorSpan(colorId),
                0,
                spannable.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            Log.d(ContentValues.TAG, spannable.toString())
            return spannable
        }

        fun statusBarOverride(activity: Activity) {
            if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
                setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
            }
            if (Build.VERSION.SDK_INT >= 19) {
                activity.window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            //make fully Android Transparent Status bar
            if (Build.VERSION.SDK_INT >= 21) {
                setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
                activity.window.statusBarColor = Color.TRANSPARENT
            }
        }

        fun changeStatusColor(activity: Activity, resourceColor: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.window.statusBarColor =
                    ContextCompat.getColor(activity.applicationContext, resourceColor)
            }
        }

        fun changeStatusTextColor(activity: Activity) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        fun dpToPx(context: Context, dp: Int): Int {
            val displayMetrics = context.resources.displayMetrics
            return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
        }

        fun pxToDp(context: Context, px: Int): Int {
            val displayMetrics = context.resources.displayMetrics
            return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
        }

        fun ratioOfScreen(context: Activity, ratio: Float): Int {
            val displayMetrics = DisplayMetrics()
            context.windowManager.defaultDisplay.getMetrics(displayMetrics)
            val height = displayMetrics.heightPixels
            val width = displayMetrics.widthPixels
            return (pxToDp(context, width) * ratio).toInt()
        }


        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val win = activity.window
            val winParams = win.attributes
            if (on) {
                winParams.flags = winParams.flags or bits
            } else {
                winParams.flags = winParams.flags and bits.inv()
            }
            win.attributes = winParams
        }

        fun shareIntent(context: Context, body: String?, share_subject: String?) {
            /*Create an ACTION_SEND Intent*/
            val intent = Intent(Intent.ACTION_SEND)
            /*This will be the actual content you wish you share.*/
            val shareBody = "Here is the share content body"
            /*The type of the content is text, obviously.*/intent.type = "text/plain"
            /*Applying information Subject and Body.*/intent.putExtra(
                Intent.EXTRA_SUBJECT,
                share_subject
            )
            intent.putExtra(Intent.EXTRA_TEXT, shareBody)
            /*Fire!*/context.startActivity(Intent.createChooser(intent, share_subject))
        }

        //    public static String getDirectionsUrl(LatLng origin, LatLng dest) {
        //
        //        // Origin of route
        //        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        //
        //        // Destination of route
        //        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        //
        //        // Sensor enabled
        //        String sensor = "sensor=false";
        //        String mode = "mode=driving";
        //        String alternative = "alternatives=true";
        //        String key = "key="+"AIzaSyBT20hQ7W44fZzKO0TwKppeqVZwUzaPBfI";
        //        // Building the parameters to the web service
        //        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode + "&" + alternative+ "&" + key;
        //
        //        // Output format
        //        String output = "json";
        //
        //        // Building the url to the web service
        //        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        //
        //        Log.e("url", url);
        //
        //        return url;
        //    }
        fun getCarBitmap(context: Context, car: Int): Bitmap? {
            val bitmap = BitmapFactory.decodeResource(context.resources, car)
            return Bitmap.createScaledBitmap(bitmap, 50, 100, false)
        }

        fun strikeText(textView: TextView) {
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

        fun Toast(context: Context?, string: String?) {
            Toast.makeText(context, string, Toast.LENGTH_LONG).show()
        }

        fun FirstLatterCap(string: String): String? {
            val fl = string.substring(0, 1).toUpperCase()
            return string.replaceFirst(string.substring(0, 1).toRegex(), fl)
        }

        fun showMessage(ctx: Context?, msg: String?) {
            Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
        }


        fun hideSoftKeyBoard(activity: Activity) {
            try {
                val inputMethodManager =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                //check if no view has focus.
                val v = activity.currentFocus ?: return
                inputMethodManager.hideSoftInputFromWindow(
                    v.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun tintColor(context: Context?, imageView: ImageView, color: Int) {
            imageView.setColorFilter(ContextCompat.getColor(context!!, color))
        }

        fun isOnline(context: Context): Boolean {
            val connManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = connManager.activeNetworkInfo
            return info?.isConnected ?: false
        }

        fun isNotEmptyNull(string: String): Boolean {
            return !TextUtils.isEmpty(string) && string != "null"
        }

        fun isEmptyNull(string: String): Boolean {
            return TextUtils.isEmpty(string) || string == "null"
        }

        fun currentDate(
            outPutFormat: String?,
            calendarView: CalendarView,
            setMin: Boolean,
            setMax: Boolean
        ): String? {
            val calendar = Calendar.getInstance()
            val date = calendar.time
            calendar[Calendar.DATE] = calendar.getActualMaximum(Calendar.DATE)
            if (setMax) {
                val endOfMonth = calendar.timeInMillis
                calendarView.maxDate = endOfMonth
            }
            if (setMin) {
                val startOfMonth = calendar.timeInMillis
                calendarView.minDate = startOfMonth
            }
            @SuppressLint("SimpleDateFormat") val df = SimpleDateFormat(outPutFormat)
            return df.format(date)
        }

        fun currentTime(outPutFormat: String?): String? {
            val calendar = Calendar.getInstance()
            val date = calendar.time
            @SuppressLint("SimpleDateFormat") val df = SimpleDateFormat(outPutFormat)
            return df.format(date)
        }

        @SuppressLint("SimpleDateFormat")
        @Throws(ParseException::class)
        fun formatDateFromDateString(
            inputDateFormat: String?,
            outputDateFormat: String?,
            inputDate: String?
        ): String? {
            val inputFormat = SimpleDateFormat(inputDateFormat)
            val outputFormat = SimpleDateFormat(outputDateFormat)
            val date = inputFormat.parse(inputDate)
            return outputFormat.format(date)
        }

        @SuppressLint("SimpleDateFormat")
        @Throws(ParseException::class)
        fun formatDateFromDateDate(
            inputDateFormat: String?,
            outputDateFormat: String?,
            inputDate: String?
        ): Date? {
            val inputFormat = SimpleDateFormat(inputDateFormat)
            val outputFormat = SimpleDateFormat(outputDateFormat)
            return inputFormat.parse(inputDate)
        }

        fun printLog(toString: String?) {
            Log.d("sdfasdfasdfas", toString!!)
        }

        fun CheckPassword(password: String?): Boolean? {
            val re = arrayOf("[a-z]", "[?=.*[@#$%!\\-_?&])(?=\\\\S+$]")
            for (r in re) {
                if (!Pattern.compile(r).matcher(password).find()) return false
            }
            return true
        }

        fun isValidEmail(email: String?): Boolean {
            val EMAIL_PATTERN = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
            val pattern = Pattern.compile(EMAIL_PATTERN)
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }

        fun StatusBarColor(context: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = context.window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = context.resources.getColor(R.color.colorPrimaryDark)
            }
        }

        fun callMethod(context: Context, number: String) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$number")
            // intent.putExtra("to")
            (context as Activity).startActivity(intent)
        }

        fun retryAlertDialog(
            mContext: Context,
            title: String?,
            msg: String?,
            firstButton: String,
            SecondButton: String,
            secondButtonClick: View.OnClickListener?
        ): AlertDialog? {
            var secondButtonClick = secondButtonClick
            val dialogBuilder = AlertDialog.Builder(mContext)
            dialogBuilder.setCancelable(false)
            val inflater = (mContext as Activity).layoutInflater
            val dialogView = inflater.inflate(R.layout.retry_alert, null)
            dialogBuilder.setView(dialogView)
            val txtRAMsg = dialogView.findViewById<View>(R.id.txtRAMsg) as TextView
            val txtRAFirst = dialogView.findViewById<View>(R.id.txtRAFirst) as TextView
            val txtRASecond = dialogView.findViewById<View>(R.id.txtRASecond) as TextView
            val deviderView = dialogView.findViewById(R.id.deviderView) as View
            txtRAMsg.text = msg
            if (firstButton.length == 0) {
                txtRAFirst.visibility = View.GONE
                deviderView.visibility = View.GONE
            } else {
                txtRAFirst.visibility = View.VISIBLE
                txtRAFirst.text = firstButton
            }
            if (SecondButton.isEmpty()) {
                txtRASecond.visibility = View.GONE
                deviderView.visibility = View.GONE
            } else {
                txtRASecond.visibility = View.VISIBLE
                txtRASecond.text = SecondButton
            }
            txtRAFirst.setOnClickListener { view: View? ->
                retryCustomAlert!!.dismiss()
                isShown = false
            }
            if (secondButtonClick == null) {
                secondButtonClick = View.OnClickListener { view: View? ->
                    retryCustomAlert!!.dismiss()
                    isShown = false
                }
            }
            txtRASecond.setOnClickListener(secondButtonClick)
            if (!isShown) {
                retryCustomAlert = dialogBuilder.create()
                retryCustomAlert!!.getWindow()!!
                    .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                retryCustomAlert!!.show()
                isShown = true
            }
            return retryCustomAlert
        }
open fun alertDialog(context: Context, title: String?, msg: String?) {
    val dialogBuilder = AlertDialog.Builder(context)
    dialogBuilder.setCancelable(false)
    val inflater = (context as Activity).layoutInflater
    val dialogView: View = inflater.inflate(R.layout.retry_alert, null)
    dialogBuilder.setView(dialogView)
    val txtRAMsg = dialogView.findViewById<View>(R.id.txtRAMsg) as TextView
    val txtRAFirst = dialogView.findViewById<View>(R.id.txtRAFirst) as TextView
    val txtRASecond = dialogView.findViewById<View>(R.id.txtRASecond) as TextView
    val deviderView = dialogView.findViewById(R.id.deviderView) as View
    val dialog = dialogBuilder.create()
    txtRAMsg.text = msg
    txtRAFirst.text = "Ok"
    txtRASecond.visibility = View.GONE
    txtRAFirst.setOnClickListener { v: View? -> dialog.dismiss() }
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.show()
}

//    public static void setImage(Context context, ImageView imageView, String url) {
//        Glide.with(context).load(url).centerCrop().placeholder(R.drawable.demo_car).into(imageView);
//    }
//
//
//    public static Animation zoomAnimation(Context context){
//        Animation animZoomIn = AnimationUtils.loadAnimation(context,R.anim.zoom_in);
//        return animZoomIn;
//    }

        //    public static AlertDialog retryAlertDialog(Context mContext, String title, String
        //            msg, String firstButton, String SecondButton, View.OnClickListener secondButtonClick) {
        //        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        //        dialogBuilder.setCancelable(false);
        //        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        //        View dialogView = inflater.inflate(R.layout.retry_alert, null);
        //        dialogBuilder.setView(dialogView);
        //
        //        TextView txtRAMsg = (TextView) dialogView.findViewById(R.id.txtRAMsg);
        //        TextView txtRAFirst = (TextView) dialogView.findViewById(R.id.txtRAFirst);
        //        TextView txtRASecond = (TextView) dialogView.findViewById(R.id.txtRASecond);
        //        View deviderView = (View) dialogView.findViewById(R.id.deviderView);
        //
        //        txtRAMsg.setText(msg);
        //
        //        if (firstButton.length() == 0) {
        //            txtRAFirst.setVisibility(View.GONE);
        //            deviderView.setVisibility(View.GONE);
        //        } else {
        //            txtRAFirst.setVisibility(View.VISIBLE);
        //            txtRAFirst.setText(firstButton);
        //        }
        //
        //        if (SecondButton.length() == 0) {
        //            txtRASecond.setVisibility(View.GONE);
        //            deviderView.setVisibility(View.GONE);
        //        } else {
        //            txtRASecond.setVisibility(View.VISIBLE);
        //            txtRASecond.setText(SecondButton);
        //        }
        //
        //        txtRAFirst.setOnClickListener(view -> {
        //            retryCustomAlert.dismiss();
        //            isShown = false;
        //        });
        //
        //        if (secondButtonClick == null) {
        //            secondButtonClick = view ->     {
        //                retryCustomAlert.dismiss();
        //                isShown = false;
        //            };
        //        }
        //        txtRASecond.setOnClickListener(secondButtonClick);
        //
        //        if (!isShown) {
        //            retryCustomAlert = dialogBuilder.create();
        //            retryCustomAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //            retryCustomAlert.show();
        //            isShown = true;
        //        }
        //        return retryCustomAlert;
        //    }
        //    public static void alertDialog(Context context, String title, String msg) {
        //        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        //        dialogBuilder.setCancelable(false);
        //        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        //        View dialogView = inflater.inflate(R.layout.retry_alert, null);
        //        dialogBuilder.setView(dialogView);
        //
        //        TextView txtRAMsg = (TextView) dialogView.findViewById(R.id.txtRAMsg);
        //        TextView txtRAFirst = (TextView) dialogView.findViewById(R.id.txtRAFirst);
        //        TextView txtRASecond = (TextView) dialogView.findViewById(R.id.txtRASecond);
        //        View deviderView = (View) dialogView.findViewById(R.id.deviderView);
        //
        //        AlertDialog dialog = dialogBuilder.create();
        //        txtRAMsg.setText(msg);
        //        txtRAFirst.setText("Ok");
        //        txtRASecond.setVisibility(View.GONE);
        //
        //        txtRAFirst.setOnClickListener(v -> dialog.dismiss());
        //
        //        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //        dialog.show();
        //    }
        //    public static void setImage(Context context, ImageView imageView, String url) {
        //        Glide.with(context).load(url).centerCrop().placeholder(R.drawable.demo_car).into(imageView);
        //    }
        //
        //
        //    public static Animation zoomAnimation(Context context){
        //        Animation animZoomIn = AnimationUtils.loadAnimation(context,R.anim.zoom_in);
        //        return animZoomIn;
        //    }
        //    public static void replaceFrg(FragmentActivity ctx, Fragment frag, boolean addToBackStack,
        //                                  int resId) {
        //        FragmentManager fm = ctx.getSupportFragmentManager();
        //        int addedFrgCount = fm.getBackStackEntryCount();
        //        FragmentTransaction ft = fm
        //                .beginTransaction();
        //        if (resId == Constans.DEFAULT_ID) {
        //            resId = R.id.cont;
        //        }
        //        ft.replace(resId, frag, frag.getClass().getName());
        //        if (addToBackStack)
        //            ft.addToBackStack(frag.getClass().getName());
        //        ft.commit();
        //    }
        fun sendSMS(context: Context, number: String?, message: String?) {
            Log.i("Send SMS", "")
            val smsIntent = Intent(Intent.ACTION_VIEW)
            smsIntent.data = Uri.parse("smsto:")
            smsIntent.type = "vnd.android-dir/mms-sms"
            smsIntent.putExtra("address", number + "")
            smsIntent.putExtra("sms_body", message)
            try {
                context.startActivity(smsIntent)
                Log.i("Finished sending SMS...", "")
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    context,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT
                ).show()
            }
        }

        fun getSelectedFile(context: Context, imageView: ImageView): File? {
            return try {
                val username = "Dropless"

                //            get bitmap from image set on  imageview and convert to byte array
                val bitmapDrawable = imageView.drawable as BitmapDrawable
                val bitmap = bitmapDrawable.bitmap
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream)
                val bitmapdata = byteArrayOutputStream.toByteArray()
                byteArrayOutputStream.flush()
                byteArrayOutputStream.close()
                val dateFormat = SimpleDateFormat("yyyyMMdd'T'HHmmss")
                val timeStamp = dateFormat.format(Date())
                val file = File(context.filesDir, username + "_profile_" + timeStamp + ".png")

                //            insert byte array into file output stream with name
                val fileOutputStream = context.openFileOutput(
                    username + "_profile_" + timeStamp + ".png",
                    Context.MODE_PRIVATE
                )
                fileOutputStream.write(bitmapdata)
                fileOutputStream.flush()
                fileOutputStream.close()
                val profileImageFile = File(file.absolutePath)
                Log.d(ContentValues.TAG, "file retrieve$profileImageFile")
                val fileUri = Uri.fromFile(profileImageFile)
                Log.d(ContentValues.TAG, "file Uri$fileUri")
                profileImageFile
            } catch (fnfe: FileNotFoundException) {
                fnfe.printStackTrace()
                null
            } catch (ioe: IOException) {
                ioe.printStackTrace()
                null
            }
        }

        fun setImage(context: Context?, imageView: ImageView?, url: String?) {
            imageView?.let {
                Glide.with(context!!).load(url).centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground).into(it)
            }
        }

        fun setRoundImage(context: Context?, imageView: ImageView?, url: String?) {
            Glide.with(context!!).load(url).circleCrop().placeholder(R.mipmap.ic_launcher)
                .into(imageView!!)
        }

        fun smsMethod(context: Context, number: String?, message: String?) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_APP_MESSAGING)
            //        intent.setData(Uri.parse("smsto:"+number)); // This ensures only SMS apps respond
//        intent.putExtra("sms_body", message);
//        if (intent.resolveActivity(context.getPackageManager()) != null) {
            (context as Activity).startActivity(intent)
//        }
        }

        fun exitFullscreen(activity: Activity) {
            if (Build.VERSION.SDK_INT > 10) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    activity.window.statusBarColor =
                        activity.resources.getColor(R.color.appThemeColor)
                }
                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            } else {
                activity.window
                    .setFlags(
                        WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
                    )
            }
        }

        fun makeTextViewResizable(
            tv: TextView,
            maxLine: Int, expandText: String, viewMore: Boolean
        ) {
            if (tv.tag == null) {
                tv.tag = tv.text
            }
            val vto = tv.viewTreeObserver
            vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val obs = tv.viewTreeObserver
                    obs.removeGlobalOnLayoutListener(this)
                    if (maxLine == 0) {
                        val lineEndIndex = tv.layout.getLineEnd(0)
                        val text = tv.text.subSequence(
                            0,
                            lineEndIndex - expandText.length + 1
                        )
                            .toString() + " " + expandText
                        tv.text = text
                        tv.movementMethod = LinkMovementMethod.getInstance()
                        tv.setText(
                            addClickablePartTextViewResizable(
                                tv.text
                                    .toString(), tv, maxLine, expandText,
                                viewMore
                            ), TextView.BufferType.SPANNABLE
                        )
                    } else if (maxLine > 0 && tv.lineCount >= maxLine) {
                        val lineEndIndex = tv.layout.getLineEnd(maxLine - 1)
                        val text = tv.text.subSequence(
                            0,
                            lineEndIndex - expandText.length + 1
                        )
                            .toString() + " " + expandText
                        tv.text = text
                        tv.movementMethod = LinkMovementMethod.getInstance()
                        tv.setText(
                            addClickablePartTextViewResizable(
                                tv.text
                                    .toString(), tv, maxLine, expandText,
                                viewMore
                            ), TextView.BufferType.SPANNABLE
                        )
                    } else {
                        val lineEndIndex = tv.layout.getLineEnd(
                            tv.layout.lineCount - 1
                        )
                        val text = tv.text.subSequence(0, lineEndIndex)
                            .toString() + " " + expandText
                        tv.text = text
                        tv.movementMethod = LinkMovementMethod.getInstance()
                        tv.setText(
                            addClickablePartTextViewResizable(
                                tv.text
                                    .toString(), tv, lineEndIndex, expandText,
                                viewMore
                            ), TextView.BufferType.SPANNABLE
                        )
                    }
                }
            })
        }

        fun addClickablePartTextViewResizable(
            strSpanned: String, tv: TextView, maxLine: Int,
            spanableText: String, viewMore: Boolean
        ): SpannableStringBuilder? {
            val ssb = SpannableStringBuilder(strSpanned)
            if (strSpanned.contains(spanableText)) {
                ssb.setSpan(
                    object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            if (viewMore) {
                                tv.layoutParams = tv.layoutParams
                                tv.setText(
                                    tv.tag.toString(),
                                    TextView.BufferType.SPANNABLE
                                )
                                tv.invalidate()
                                makeTextViewResizable(
                                    tv, -5, "...Read Less",
                                    false
                                )
                                tv.setTextColor(Color.BLACK)
                            } else {
                                tv.layoutParams = tv.layoutParams
                                tv.setText(
                                    tv.tag.toString(),
                                    TextView.BufferType.SPANNABLE
                                )
                                tv.invalidate()
                                makeTextViewResizable(
                                    tv, 5, "...Read More",
                                    true
                                )
                                tv.setTextColor(Color.BLACK)
                            }
                        }
                    }, strSpanned.indexOf(spanableText),
                    strSpanned.indexOf(spanableText) + spanableText.length, 0
                )
            }
            return ssb
        }
    }

}