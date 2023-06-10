package com.example.lostku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lostku.databinding.ActivityLostListBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LostListActivity : AppCompatActivity() {
    lateinit var binding : ActivityLostListBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: LostRecyclerViewAdapter
    lateinit var rdb:DatabaseReference
    var findQuery = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLostListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false )
        rdb = Firebase.database.getReference("Lost/info") //Lost DB에 info 테이블 생성 후 참조
        val query = rdb.limitToLast(50) //최근 50개 가져오는 쿼리
        val option
        = FirebaseRecyclerOptions.Builder<LostData>().setQuery(query,LostData::class.java).build()
        adapter = LostRecyclerViewAdapter(option)
        adapter.itemClickListener = object :LostRecyclerViewAdapter.OnItemClickListener{
            override fun OnItemClick(position: Int, data :LostData) {
                //deleteBtn 클릭했을 때 DB에서 삭제
                rdb.child(data.id.toString()).removeValue()

            }

        }
        binding.apply {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
            //더미데이터 생성
            val lost1 = LostData(1,"test1","test1","test1","test1","test1")
            val lost2 = LostData(2,"test2","test2","test2","test2","test2")
            val lost3 = LostData(3,"test3","test3","test3","test3","test3")
            val lost4 = LostData(4,"test4","test4","test4","test4","test4")
            rdb.child("1").setValue(lost1)
            rdb.child("2").setValue(lost2)
            rdb.child("3").setValue(lost3)
            rdb.child("4").setValue(lost4)

            searchBtn.setOnClickListener{
                if(!findQuery)
                    findQuery = true
                if(adapter!=null)
                    adapter.stopListening()
                val query = rdb.orderByChild("name").equalTo(searchText.text.toString())
                val option
                        = FirebaseRecyclerOptions.Builder<LostData>().setQuery(query,LostData::class.java).build()
                adapter = LostRecyclerViewAdapter(option)
                recyclerView.adapter = adapter
                adapter.startListening()
                clearInput()
            }


        }
    }

    fun clearInput(){
        binding.apply {
            searchText.text.clear()
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    fun initQuery(){
        //등록,삭제,업데이트 할때
        if(findQuery) {
            findQuery = false
            if (adapter != null)
                adapter.stopListening()
            val query = rdb.limitToLast(50)
            val option =
                FirebaseRecyclerOptions.Builder<LostData>().setQuery(query, LostData::class.java)
                    .build()
            adapter = LostRecyclerViewAdapter(option)
            binding.recyclerView.adapter = adapter
            adapter.startListening()
        }
    }
}