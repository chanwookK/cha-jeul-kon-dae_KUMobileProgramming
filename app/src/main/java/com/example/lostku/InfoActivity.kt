package com.example.lostku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lostku.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityInfoBinding
    lateinit var  adapter: InfoRecyclerViewAdapter//dataAdapter와 연결시켜주는 변수
    val data:ArrayList<InfoData> = ArrayList()//MyData set
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_info)
        init()
        initRecyclerView()

    }

    private fun init(){
        data.add(InfoData("분실물 보관 단체", "위치"))
        data.add(InfoData("중앙동아리연합회", "제2학생회관 218호"))
        data.add(InfoData("중앙동아리연합회", "제2학생회관 218호"))
        data.add(InfoData("중앙동아리연합회", "제2학생회관 218호"))
        data.add(InfoData("중앙동아리연합회", "제2학생회관 218호"))
        //데이터 추가
    }
    private fun initRecyclerView() {
        binding.ListRecyclerview.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        adapter = InfoRecyclerViewAdapter(data)


        binding.ListRecyclerview.adapter = adapter


    }
}