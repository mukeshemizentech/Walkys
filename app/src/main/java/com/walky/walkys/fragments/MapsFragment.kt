package com.walky.walkys.fragments

import android.Manifest
import android.app.Activity
import android.content.Context.SENSOR_SERVICE
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.walky.responses.HomeResponse
import com.walky.utils.Utils
import com.walky.utils.drawableBackColor
import com.walky.walkys.R
import com.walky.walkys.activities.SuccessActivity
import com.walky.walkys.adapters.DogListAdapter
import com.walky.walkys.adapters.ViewPageTabAdapter
import com.walky.walkys.databinding.FragmentMapsBinding
import com.walky.walkys.interfaces.StepListener
import com.walky.walkys.sensors.StepDetector
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class MapsFragment : Fragment(), OnMapReadyCallback, SensorEventListener, StepListener {

    lateinit var binding: FragmentMapsBinding
    var latitude = 0.0
    var longitude = 0.0
    private val DEFAULT_ZOOM = 15f
    var searchadd = false
    lateinit var mMap: GoogleMap
    lateinit var mapView: View
    lateinit var center: LatLng
    lateinit var mLastKnowLocation: Location
    lateinit var locationCallback: LocationCallback
    lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    lateinit var geocoder: Geocoder
    lateinit var addresses: List<Address>
    lateinit var pinCode: String
    lateinit var addressString: kotlin.String

    var temp = ""
    private var viewPage_tabAdapter: ViewPageTabAdapter? = null
    private var adapter: DogListAdapter? = null
    private val arrayList: ArrayList<HomeResponse> = ArrayList<HomeResponse>()

    private var simpleStepDetector: StepDetector? = null
    private var sensorManager: SensorManager? = null
    private var accel: Sensor? = null
    private val TEXT_NUM_STEPS = "Steps: "
    private var numSteps = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false)

        // Get an instance of the SensorManager

        // Get an instance of the SensorManager
        sensorManager = requireContext().getSystemService(SENSOR_SERVICE) as SensorManager
        accel = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        simpleStepDetector = StepDetector()
        simpleStepDetector!!.registerListener(this)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        if (mapFragment != null) {
            mapFragment.getMapAsync(this)
            mapView = mapFragment.requireView()
        }
        DogListAdapter.isSelected = false
        binding.fillRipple.startRippleAnimation()
        binding.stepsTv.visibility = View.VISIBLE
        binding.chronometer.visibility = View.INVISIBLE
        binding.recyclerView.visibility = View.VISIBLE
        binding.selectPetTv.visibility = View.VISIBLE
        binding.startBtn.setOnClickListener {
            if (DogListAdapter.isSelected!!) {
                if (binding.startBtn.text.contains("start", true)) {
                    binding.startBtn.text = "stop"
                    requireContext().drawableBackColor(binding.startBtn, Color.RED)

                    binding.chronometer.visibility = View.VISIBLE
                    binding.stepsTv.visibility = View.VISIBLE
                    binding.selectPetTv.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    TimerStart()
                    numSteps = 0
                    sensorManager!!.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST);

                } else {
                    binding.stepsTv.visibility = View.INVISIBLE
                    binding.chronometer.visibility = View.INVISIBLE
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.selectPetTv.visibility = View.VISIBLE
                    binding.startBtn.text = "start"
                    binding.chronometer.stop()
                    requireContext().drawableBackColor(binding.startBtn, resources.getColor(R.color.colorPrimary))
                    sensorManager!!.unregisterListener(this);
                    startActivity(Intent(requireContext(), SuccessActivity::class.java))
                }
            } else {
                Utils.alertDialog(
                    requireContext(),
                    resources.getString(R.string.app_name),
                    "Please select the pet"
                )
            }

        }


        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
            requireActivity()!!
        )

//

//

        binding.recyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        adapter = DogListAdapter(requireContext(), arrayList, "mapFragment")
        binding.recyclerView.adapter = adapter


        return binding.getRoot()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector!!.updateAccel(
                event.timestamp, event.values[0], event.values[1], event.values[2]
            )
        }
    }
    override fun onStart() {
        super.onStart()
        if (binding.startBtn.text.contains("start", true)) {
            requireContext().drawableBackColor(binding.startBtn, resources.getColor(R.color.colorPrimary))
        }else{
            requireContext().drawableBackColor(binding.startBtn, Color.RED)
        }
    }

    override fun step(timeNs: Long) {
        numSteps++
        binding.stepsTv.setText(TEXT_NUM_STEPS + numSteps)
    }

    private fun TimerStart() {
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
//        binding.chronometer.base =
//            SystemClock.elapsedRealtime() - (hour * 60000 * 60 + minute * 60000 + second * 1000)
        binding.chronometer.start()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    activity, R.raw.style_json
                )
            )
            if (!success) {
                Log.e("", "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("", "Can't find style. Error: ", e)
        }
        mMap = googleMap
        mMap.getUiSettings().isMyLocationButtonEnabled = true
        mMap.getUiSettings().isScrollGesturesEnabledDuringRotateOrZoom = false
        // For showing a move to my location button
        if (ActivityCompat.checkSelfPermission(
                requireActivity()!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireActivity()!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity()!!,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
        } else {
            googleMap.isMyLocationEnabled = true
        }

//        mMap.clear();
        if (!searchadd) {
            mMap.setOnCameraIdleListener(OnCameraIdleListener {
                center = mMap.cameraPosition.target
                mMap.clear()
                try {
                    if (!searchadd) {
                        GetLocationAsync(center.latitude, center.longitude, geocoder).execute()
                    }
                } catch (e: Exception) {
                }
            })
            //            mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
//
//                @Override
//                public void onCameraChange(CameraPosition arg0) {
//                      center = mMap.getCameraPosition().target;
//
//                    mMap.clear();
//                    try {
//                        new GetLocationAsync(center.latitude, center.longitude)
//                                .execute();
//
//                    } catch (Exception e) {
//                    }
//                }
//            });
        }
        if (mapView != null && mapView!!.findViewById<View?>("1".toInt()) != null) {
            val locationButton =
                (mapView!!.findViewById<View>("1".toInt()).parent as View).findViewById<View>("2".toInt())
            val layoutParams = locationButton.layoutParams as RelativeLayout.LayoutParams
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
            layoutParams.setMargins(0, 0, 40, 180)


            //check if gps is enabled or not then request user to enable it.
            val locationRequest = LocationRequest.create()
            locationRequest.interval = 10000
            locationRequest.fastestInterval = 5000
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            val settingsClient = LocationServices.getSettingsClient(requireActivity()!!)
            val task = settingsClient.checkLocationSettings(builder.build())
            task.addOnSuccessListener(
                requireActivity()!!
            ) { getDeviceLocation() }
            task.addOnFailureListener(requireActivity()!!) { e ->
                if (e is ResolvableApiException) {
                    try {
                        e.startResolutionForResult(requireActivity(), 51)
                    } catch (ex: SendIntentException) {
                        ex.printStackTrace()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("result_res", data!!.data.toString())
        if (requestCode == 51) {
            if (resultCode == Activity.RESULT_OK) {
                getDeviceLocation()
            }
        }
    }

    fun getDeviceLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity()!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity()!!, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            getDeviceLocation()
            return
        }
        mFusedLocationProviderClient!!.lastLocation.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {
                mLastKnowLocation = task.result!!
                if (mLastKnowLocation != null) {
                    mMap!!.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                mLastKnowLocation.getLatitude(),
                                mLastKnowLocation.getLongitude()
                            ), DEFAULT_ZOOM
                        )
                    )
                } else {
                    val locationRequest = LocationRequest.create()
                    locationRequest.interval = 10000
                    locationRequest.fastestInterval = 5000
                    locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    locationCallback = object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            super.onLocationResult(locationResult)
                            if (locationResult == null) {
                                return
                            }
                            mLastKnowLocation = locationResult.lastLocation
                            latitude = mLastKnowLocation.getLatitude()
                            longitude = mLastKnowLocation.getLongitude()
                            mMap!!.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        latitude,
                                        longitude
                                    ), DEFAULT_ZOOM
                                )
                            )
                            mFusedLocationProviderClient!!.removeLocationUpdates(locationCallback)
                        }
                    }
                    if (ActivityCompat.checkSelfPermission(
                            requireActivity()!!,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            requireActivity()!!, Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return@OnCompleteListener
                    }
                    mFusedLocationProviderClient!!.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        null
                    )
                }
            } else {
                Toast.makeText(requireActivity(), "unable to get last location", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    inner class GetLocationAsync(var x: Double, var y: Double, var geocoder: Geocoder) :
        AsyncTask<String?, Void?, String?>() {
        var str: StringBuilder? = null
        override fun onPreExecute() {
//            binding.locationTv.setText(" Getting location... ");
        }
//        override fun doInBackground(vararg p0: String?): String? {
//            TODO("Not yet implemented")
//        }

        override fun doInBackground(vararg params: String?): String? {

            try {
                geocoder = Geocoder(activity, Locale.ENGLISH)
                addresses = geocoder.getFromLocation(x, y, 1)
                str = java.lang.StringBuilder()
                if (Geocoder.isPresent()) {
                    if (addresses.isNotEmpty()) {
                        val returnAddress: Address = addresses.get(0)
                        val localityString = returnAddress.locality
                        val city = returnAddress.countryName
                        val region_code = returnAddress.countryCode
                        val zipcode = returnAddress.postalCode
                        str!!.append(localityString + "")
                        str!!.append(city + "" + region_code + "")
                        str!!.append(zipcode + "")
                    } else {
                    }
                } else {
                }
            } catch (e: IOException) {
                Log.e("tag", e.message!!)
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            try {
                if (addresses.isNotEmpty()) {
//                    binding.locationTv.setText(addresses.get(0).getAddressLine(0) + " ");
                    latitude = center.latitude
                    longitude = center.longitude
                    pinCode = addresses.get(0).getPostalCode()
                    addressString = addresses.get(0).getAddressLine(0) + " "
                    //                    Prefs.putString(Constans.Address, addresses.get(0).getAddressLine(0));
                    Log.i(
                        "",
                        "Place_onCameraMove:  " + center.latitude + "    " + center.longitude + " picCode: " + pinCode
                    )
                } else {

//                    binding.locationTv.setText("Location not found");
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

        protected fun onProgressUpdate(vararg values: Void) {}

    }


}