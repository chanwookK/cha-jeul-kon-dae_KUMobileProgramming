package com.example.lostku

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lostku.databinding.ActivityLostListBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage

class LostListActivity : AppCompatActivity() {
    lateinit var binding : ActivityLostListBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: LostRecyclerViewAdapter
    lateinit var rdb:DatabaseReference
    lateinit var pdb:DatabaseReference

    var findQuery = false

    lateinit var photoDialog: ShowPhotoDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLostListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false )
        rdb = Firebase.database.getReference("Lost/info") //Lost DB에 info 테이블 생성 후 참조
//        pdb = Firebase.database.getReference("PW")
//        pdb.child("총학생회").setValue("Mjg1Ng==")
//        pdb.child("건국문학예술학생연합").setValue("MzQ5Mgo=")
//        pdb.child("동아리연합회").setValue("MzQ5NQ==")
//        pdb.child("학생복지위원회").setValue("NDY1Ng==")
//        pdb.child("문과대학학생회").setValue("NTI1Nw==")
//        pdb.child("이과대학학생회").setValue("NTQ3OA==")
//        pdb.child("건축대학학생회").setValue("NjI1OQ==")
//        pdb.child("공과대학학생회").setValue("Njk4Mg==")
//        pdb.child("사회과학대학학생회").setValue("NzU1NA==")
//        pdb.child("융합과학기술원학생회").setValue("NzcxMw==")
        val query = rdb.limitToLast(50) //최근 50개 가져오는 쿼리
        photoDialog = ShowPhotoDialog(this)

        val option
        = FirebaseRecyclerOptions.Builder<LostData>().setQuery(query,LostData::class.java).build()
        adapter = LostRecyclerViewAdapter(option)
        adapter.itemClickListener = object :LostRecyclerViewAdapter.OnItemClickListener{
            override fun OnItemClick(position: Int, data :LostData) {
                // Create a layout to hold the EditText
                val layout = FrameLayout(this@LostListActivity)
                val pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250f, resources.displayMetrics).toInt() // DP를 Pixel로 변환
                val input = EditText(this@LostListActivity).apply {
                    layoutParams = FrameLayout.LayoutParams(
                        pixels, // Width
                        FrameLayout.LayoutParams.WRAP_CONTENT  // Height
                    ).apply {
                        gravity = Gravity.CENTER // Center the EditText in the AlertDialog
                    }
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }
                layout.addView(input)

                // Create AlertDialog with custom view
                val dialog = android.app.AlertDialog.Builder(this@LostListActivity)
                    .setTitle("비밀번호 입력")
                    .setMessage("비밀번호를 입력하세요")
                    .setView(layout)
                    .setPositiveButton("확인") { dialog, _ ->
                        // 비밀번호를 처리하는 코드
                        val password = input.text.toString()
                        // 비밀번호 확인
                        if (password == "1234") {  // 실제 비밀번호로 변경
                            //deleteBtn 클릭했을 때 DB에서 삭제
                            rdb.child(data.id.toString()).removeValue()
                        } else {
                            Toast.makeText(applicationContext, "비밀번호가 잘못되었습니다", Toast.LENGTH_SHORT).show()
                        }
                        dialog.dismiss()
                    }
                    .setNegativeButton("취소") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()

                // Set AlertDialog background color to light grey
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.argb(200, 200, 200, 200)))

                // Show the AlertDialog
                dialog.show()
            }

            override fun OnPhotoClick(position: Int, data: LostData) {
                photoDialog.show(data.photo.toUri())
            }

        }
        binding.apply {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
            //더미데이터 생성
//            val lost1 = LostData(1,"test1","test1","test1","test1","test1")
//            val lost2 = LostData(2,"test2","test2","test2","test2","test2")
//            val lost3 = LostData(3,"test3","test3","test3","test3","test3")
//            val lost4 = LostData(4,"test4","test4","test4","test4","test4")
//            rdb.child("1").setValue(lost1)
//            rdb.child("2").setValue(lost2)
//            rdb.child("3").setValue(lost3)
//            rdb.child("4").setValue(lost4)

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
            listBtn.setOnClickListener{
                adapter = LostRecyclerViewAdapter(option)
                recyclerView.adapter = adapter
                adapter.startListening()
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