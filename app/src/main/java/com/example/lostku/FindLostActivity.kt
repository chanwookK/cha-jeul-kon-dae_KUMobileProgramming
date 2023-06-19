package com.example.lostku

import android.content.Context.LOCATION_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import com.example.lostku.databinding.ActivityFindLostBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.*


class FindLostActivity : AppCompatActivity() {

    lateinit var binding: ActivityFindLostBinding
    lateinit var googleMap: GoogleMap
    var loc = LatLng(37.554752, 126.970631)

    //서울역
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationRequest2: LocationRequest
    lateinit var locationCallback: LocationCallback

    var startupdate = false
    var flag = 0

    val permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val gpeSettingLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (checkGPSProvider()) {
                //getLastLocation()
                //setCurrentLocation(loc)
                startLocationUpdate()
            } else {
                //val intent1 = Intent(this@FindLostActivity,MainActivity::class.java)
                //startActivity(intent1)
                setCurrentLocation(loc)
                //initLocation()
            }
        }

    val locationPermissionRequest =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false) ||
                        permissions.getOrDefault(
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            false
                        ) -> {
                    //getLastLocation()
                    startLocationUpdate()

                   // val intent1 = Intent(this@FindLostActivity,MainActivity::class.java)
                 //   startActivity(intent1)
                    //showPermissionRequestDlg()
                    //showGPSSetting()
                }

                else -> {
//                    //showGPSSetting()
//                    //setCurrentLocation(loc)
//                    if(ContextCompat.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) !startupdate){
//                        //showGPSSetting()
//                        setCurrentLocation(loc)
//                    }else{
//                       showGPSSetting()
//                    }
                    //locationPermissionRequest.launch(permissions)
                    //startLocationUpdate()

                    //startLocationUpdate()
                    //startLocationUpdate()

                    //startLocationUpdate()
                    setCurrentLocation(loc)
                    //showGPSSetting()
                    //initstart()
                    //chekcout()
                }


            }
        }



    var LData: ArrayList<SaveLocation> = ArrayList()
    var cur_lat: Double? = loc.latitude
    var cur_lon: Double? = loc.longitude


    private fun checkFineLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkCoarseLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindLostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //makeTable()
        initstart()

        //initLocation()
        //initLayout()
    }
    private fun initstart(){
        //showGPSSetting()
        initLocation()
        //startLocationUpdate()
        //showPermissionRequestDlg()
        //showGPSSetting()
       // initLocation()
    }

//    private fun initmap(){
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync{
//            googleMap = it
//            initLocation()
//        }
//    }

    fun initLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setMinUpdateIntervalMillis(5000).build()
//        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
//            .setMinUpdateIntervalMills(5000).build()
        locationRequest2 = LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 10000)
            .setMinUpdateIntervalMillis(5000).build()

        locationCallback = object : LocationCallback(){
            override fun onLocationResult(location: LocationResult) {
                if (location.locations.size==0) return
                loc = LatLng(
                    location.locations[location.locations.size-1].latitude,
                    location.locations[location.locations.size-1].longitude
                )
                setCurrentLocation(loc)
                //Log.i("location","LocationCallback()")
            }
        }
        //getLastLocation()
    }

    override fun onResume() {
        super.onResume()
        //Log.i("location","onResume()")
        if(!startupdate)
            startLocationUpdate()
    }

    override fun onPause() {
        super.onPause()
      //  Log.i("location","onPause()")
        stopLocationUpdate()
    }

    private fun stopLocationUpdate(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        startupdate = false
        //Log.i("location","stopLocationUpdate()")
    }

    private fun startLocationUpdate(){
        when{
            checkFineLocationPermission() -> {
                if (!checkGPSProvider()) {
                    showGPSSetting()
                } else {
                    startupdate = true
                    fusedLocationProviderClient.requestLocationUpdates(
                        locationRequest, locationCallback, Looper.getMainLooper()
                    )
                   // Log.i("location","startLocationUpdates()")
                }
            }


            checkCoarseLocationPermission() -> {

                    startupdate = true
                    fusedLocationProviderClient.requestLocationUpdates(
                        locationRequest2, locationCallback, Looper.getMainLooper()
                    )
                    //Log.i("location2", "startLocationUpdates()")


            }


            ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                //startLocationUpdate()
                showPermissionRequestDlg()
                //showGPSSetting()
            }
            else -> {
                //showPermissionRequestDlg()
                //showGPSSetting()
                locationPermissionRequest.launch(permissions)
            }

        }

    }

    fun setCurrentLocation(location: LatLng) {

            cur_lat = loc.latitude
            cur_lon = loc.longitude
            initlists()
            makeTable()




    }




    private fun checkGPSProvider(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
    }

    private fun showGPSSetting() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage(
            "해당 기능을 사용하기 위해서는 위치 서비스가 필요합니다.\n" +
                    "위치 설정을 허용하겠습니까?"
        )
        builder.setPositiveButton("설정", DialogInterface.OnClickListener { dialog, id ->
            val GpsSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            gpeSettingLauncher.launch(GpsSettingIntent)
            //locationPermissionRequest.launch(permissions)
            //showPermissionRequestDlg()
            //startLocationUpdate()


        })
        builder.setNegativeButton("취소",
            DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()

                //setCurrentLocation(loc)
                //val intent1 = Intent(this@FindLostActivity,MainActivity::class.java)
                //startActivity(intent1)
            })
        builder.create().show()

    }

    private fun showPermissionRequestDlg() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("위치 서비스 제공")
        builder.setMessage(
            "앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" +
                    "기기의 위치를 제공하도록 설정하시겠습니까?"
        )
        builder.setPositiveButton("설정", DialogInterface.OnClickListener { dialog, id ->
            //val GpsSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            //gpeSettingLauncher.launch(GpsSettingIntent)
            locationPermissionRequest.launch(permissions)
            //val GpsSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            //gpeSettingLauncher.launch(GpsSettingIntent)

        })
        builder.setNegativeButton("취소",
            DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()
                //setCurrentLocation(loc)
                //val intent1 = Intent(this@FindLostActivity,MainActivity::class.java)
                //startActivity(intent1)

            })
        builder.create().show()


    }






        fun makeTable() {
            //initLocation()

            LData.sortBy { it.location }
            val tableLayout = binding.tableLayout
            for (address in LData) {
                val tableRow = TableRow(this)
                val textView1 = TextView(this)
                textView1.setTextSize(30F)
                textView1.text = address.name
                textView1.setBackgroundResource(R.drawable.tableline)
                val textView2 = TextView(this)
                textView2.text = address.location.toString() + "M"
                textView2.setTextSize(33F)
                textView2.setBackgroundResource(R.drawable.tableline)
                tableRow.addView(textView1)
                tableRow.addView(textView2)

                tableLayout.addView(tableRow)
            }
        }

        fun ClearTable() {


            val tableLayout = binding.tableLayout
            //tableLayout.removeAllViews()
            tableLayout.removeViews(1, tableLayout.childCount - 1)

        }

        fun initlists() {
            ClearTable()
            LData.removeAll(LData)
            val scan = Scanner(resources.openRawResource(R.raw.lists))

            while (scan.hasNextLine()) {
                val name = scan.nextLine()

                val location1 = scan.nextLine().toDouble()
                val location2 = scan.nextLine().toDouble()
                var location3 = haversine(cur_lat!!, cur_lon!!, location1, location2)
                LData.add(SaveLocation(name, location3.toInt()))

            }
            //Toast.makeText(this, "Latitude: $cur_lat, Longitude: $cur_lon", Toast.LENGTH_SHORT)
              //  .show()
            //makeTable()
        }

    fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6372.8 * 1000 // radius of Earth in meters
        val l1 = Math.toRadians(lat1)
        val l2 = Math.toRadians(lat2)
        val dl = Math.toRadians(lat2 - lat1)
        val dr = Math.toRadians(lon2 - lon1)

        val a = sin(dl / 2).pow(2.0) + cos(l1) * cos(l2) * sin(dr / 2).pow(2.0)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return r * c
    }


    }



