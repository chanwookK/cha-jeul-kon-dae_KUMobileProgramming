package com.example.lostku

import android.app.Dialog
import android.net.Uri
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.lostku.databinding.CustomImageDlgBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ShowPhotoDialog (private val context : AppCompatActivity){
    lateinit var binding: CustomImageDlgBinding
    lateinit var rdb: DatabaseReference
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감

    fun show(photoUri : Uri){
        binding = CustomImageDlgBinding.inflate(context.layoutInflater)
        rdb = Firebase.database.getReference("Lost/info")

//        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(binding.root)                    //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)                            //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

//        Log.i("", "PHOTO : "+data.photo)
        Glide.with(binding.myPhoto).load(photoUri).into(binding.myPhoto)
//        binding.myPhoto.setImageURI(photoUri)
        dlg.show()

        binding.apply {
            exitBtn.setOnClickListener {
                dlg.dismiss()
            }
        }
    }

    private fun setImage(index : Int)
    {

    }
}