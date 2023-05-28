package com.example.lostku


import android.app.ProgressDialog.show
import android.location.Geocoder
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.lostku.databinding.ActivityFindLostBinding

class FindLostActivity : AppCompatActivity() {
    lateinit var binding: ActivityFindLostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindLostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        var sData = resources.getStringArray(R.array.building)
        var sadapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sData)
        binding.buildings.adapter = sadapter
        binding.buildings.setOnItemSelectedListener(CustomOnItemSelectedListener())

// LocationManager 객체를 가져옵니다.
//        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
//
//// 사용자의 현재 위치를 가져옵니다.
//        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//
//// 가까운 장소를 검색합니다.
//        val geocoder = Geocoder(this)
//        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
//
//// 검색된 주소를 표에 표시합니다.
//        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
//        for (address in addresses) {
//            val tableRow = TableRow(this)
//            val textView1 = TextView(this)
//            textView1.text = address.featureName
//            val textView2 = TextView(this)
//            textView2.text = address.getAddressLine(0)
//            tableRow.addView(textView1)
//            tableRow.addView(textView2)
//            tableLayout.addView(tableRow)
//        }
//    }


    }

    inner class CustomOnItemSelectedListener: AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            /*Toast.makeText(parent?.context,parent?.getItemAtPosition(position).toString(),
            Toast.LENGTH_SHORT).show()
            binding.recyclerView.adapter = MyCafeteriaAdapter(CData)*/
            when (position) {
                0 -> Toast.makeText(this@FindLostActivity,"0!!!",Toast.LENGTH_SHORT).show()
                    //binding.recyclerView.adapter = adapter1
                1 -> Toast.makeText(this@FindLostActivity,"1!!!",Toast.LENGTH_SHORT).show()

                2 -> Toast.makeText(this@FindLostActivity,"2!!!",Toast.LENGTH_SHORT).show()
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }

    }
}
