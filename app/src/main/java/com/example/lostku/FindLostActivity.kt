package com.example.lostku


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lostku.databinding.ActivityFindLostBinding
import java.util.*
import kotlin.collections.ArrayList


class FindLostActivity : AppCompatActivity() {

    lateinit var binding: ActivityFindLostBinding
    var LData : ArrayList<SaveLocation> = ArrayList()
    var location=0
    var cur_lat: Double? = null
    var cur_lon: Double? = null
    private val R2 = 6372.8 * 1000
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            if (ContextCompat.checkSelfPermission(this@FindLostActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
                val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val loc_Current = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                cur_lat = loc_Current?.latitude
                cur_lon = loc_Current?.longitude

                Log.d("Location Update", "Latitude: $cur_lat, Longitude: $cur_lon")

                initlists()
            }
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindLostBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initLayout()
    }



    private fun makeTable() {

        LData.sortBy { it.location }
        val tableLayout = binding.tableLayout
        for (address in LData) {
            val tableRow = TableRow(this)
            val textView1 = TextView(this)
            textView1.setTextSize(30F)
            textView1.text = address.name
            textView1.setBackgroundResource(R.drawable.tableline)
            val textView2 = TextView(this)
            textView2.text = address.location.toString()+"M"
            textView2.setTextSize(33F)
            textView2.setBackgroundResource(R.drawable.tableline)
            tableRow.addView(textView1)
            tableRow.addView(textView2)

            tableLayout.addView(tableRow)
        }
    }
    private fun ClearTable() {


        val tableLayout = binding.tableLayout
        //tableLayout.removeAllViews()
        tableLayout.removeViews(1,tableLayout.childCount-1)

    }

    private fun initlists() {
        ClearTable()
        LData.removeAll(LData)
        val scan = Scanner(resources.openRawResource(R.raw.lists))

        while (scan.hasNextLine())
        {
            val name = scan.nextLine()

            val location1 = scan.nextLine().toDouble()
            val location2 =scan.nextLine().toDouble()
            var location3 = getDistance(cur_lat!!, cur_lon!!,location1,location2)
            LData.add(SaveLocation(name, location3.toInt()))

        }
        Toast.makeText(this, "Latitude: $cur_lat, Longitude: $cur_lon", Toast.LENGTH_SHORT).show()
        makeTable()
    }
    fun getDistance( lat1: Double, lng1:Double, lat2:Double, lng2:Double) : Float{

        val myLoc = Location(LocationManager.NETWORK_PROVIDER)
        val targetLoc = Location(LocationManager.NETWORK_PROVIDER)
        myLoc.latitude= lat1
        myLoc.longitude = lng1

        targetLoc.latitude= lat2
        targetLoc.longitude = lng2

        return myLoc.distanceTo(targetLoc)
    }


    private fun initLayout() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
            initlists()
        } else {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER?: LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)
        }

       }

//
}
