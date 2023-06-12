package com.example.lostku

import android.app.Dialog
import android.net.Uri
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.lostku.databinding.CustomImageDlgBinding

class ShowPhotoDialog (private val context : AppCompatActivity){
    lateinit var binding: CustomImageDlgBinding
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감

    fun show(photoUri : Uri){

        binding = CustomImageDlgBinding.inflate(context.layoutInflater)

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(binding.root)                    //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)                            //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        binding.myPhoto.setImageURI(photoUri)

        binding.apply {
            exitBtn.setOnClickListener {
                dlg.dismiss()
            }
        }
    }
}