package com.example.lostku


import android.app.ProgressDialog.show
import android.graphics.Color
import android.location.Geocoder
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.lostku.databinding.ActivityFindLostBinding
import java.util.*

class FindLostActivity : AppCompatActivity() {
    lateinit var binding: ActivityFindLostBinding

    var LData : ArrayList<SaveLocation> = ArrayList()
    var location=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindLostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initlists()
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
            val locations = scan.nextLine().toInt() - location
            LData.add(SaveLocation(name, locations))

        }

        makeTable()

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


//
//
       }

    inner class CustomOnItemSelectedListener: AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            /*Toast.makeText(parent?.context,parent?.getItemAtPosition(position).toString(),
            Toast.LENGTH_SHORT).show()
            binding.recyclerView.adapter = MyCafeteriaAdapter(CData)*/
            when (position) {
                0 -> {Toast.makeText(this@FindLostActivity,"0!!!",Toast.LENGTH_SHORT).show()
                        location = 5
                        initlists()

                        }
                    //binding.recyclerView.adapter = adapter1
                1 -> {
                    Toast.makeText(this@FindLostActivity, "1!!!", Toast.LENGTH_SHORT).show()
                    location = 7
                        initlists()
                    }

                2 -> {
                    Toast.makeText(this@FindLostActivity, "2!!!", Toast.LENGTH_SHORT).show()
                    location = 9
                        initlists()
                    }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }

    }
}
