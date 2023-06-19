package com.example.lostku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lostku.databinding.ActivityKeywordBinding
import com.example.lostku.databinding.ActivityMainBinding

class KeywordActivity : AppCompatActivity() {
    lateinit var binding: ActivityKeywordBinding
    lateinit var  adapter: KeywordRecyclerViewAdapter//dataAdapter와 연결시켜주는 변수
    val data:ArrayList<KeywordData> = ArrayList()//MyData set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKeywordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        init()



    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        adapter = KeywordRecyclerViewAdapter(data)

        //리사이클러 뷰 아이템 클릭시 이벤트 처리
        adapter.itemClickListener = object :KeywordRecyclerViewAdapter.OnItemClickListener {
            override fun OnItemClick(position: Int) {
                binding.registeredKewardText.setText(adapter.keyNum.toString() + "/5")

            }
        }

        binding.recyclerView.adapter = adapter

    }

    fun init(){
        //등록버튼 처리
        binding.registerBtn.setOnClickListener {
            if(adapter.keyNum == 5)
                Toast.makeText(this@KeywordActivity,"최대 등록 갯수를 초과했습니다.",Toast.LENGTH_SHORT).show()
            else {
                val insertText = binding.keywordRegisterEditText.text.toString()
                adapter.addItems(insertText)
                adapter.keyNum++
                binding.registeredKewardText.setText(adapter.keyNum.toString() + "/5")
            }

        }
    }
}