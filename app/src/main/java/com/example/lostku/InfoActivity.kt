package com.example.lostku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        setContentView(binding.root)
        init()
        initRecyclerView()


    }



    private fun init(){

        data.add(InfoData("총학생회", "제1학생회관 301호"))
        data.add(InfoData("건국문학예술학생연합", "제2학생회관 B109호"))
        data.add(InfoData("동아리연합회", "제2학생회관 218호"))
        data.add(InfoData("학생복지위원회", "제1학생회관 B110호"))
        data.add(InfoData("문과대학학생회", "인문학관 102호"))
        data.add(InfoData("이과대학학생회", "과학관 B127호"))
        data.add(InfoData("건축대학학생회", "건축관 105호"))
        data.add(InfoData("공과대학학생회", "공학관A동 201호"))
        data.add(InfoData("사회과학대학학생회", "상허연구관 209호"))
        data.add(InfoData("융합과학기술원학생회", "생명과학관 105호"))
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