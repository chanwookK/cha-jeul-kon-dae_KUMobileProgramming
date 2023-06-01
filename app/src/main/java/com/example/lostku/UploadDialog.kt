package com.example.lostku

import android.app.Dialog
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lostku.databinding.ActivityMainBinding
import com.example.lostku.databinding.ActivityUploadBinding
import com.example.lostku.databinding.CustomUploadDlgBinding

class UploadDialog(private val context : AppCompatActivity) {

    lateinit var binding: CustomUploadDlgBinding
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감

    lateinit var uploadBinding: ActivityUploadBinding

    fun show(uploadActivity: UploadActivity) {
        binding = CustomUploadDlgBinding.inflate(context.layoutInflater)
        uploadBinding = ActivityUploadBinding.inflate(context.layoutInflater)

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(binding.root)                    //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)                            //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        clearText()

        binding.apply {
            submitBtn.setOnClickListener {

                if(isEmptyTextExist())
                {
                    Toast.makeText(context, "모든 칸을 입력해주세요!", Toast.LENGTH_SHORT).show()
                }
                else {
                    // TODO: DB에 만들어서 올릴 준비.
                    uploadActivity.isInfoSubmitted = true

                    // 분실물 등록의 텍스트 수정
                    uploadBinding.stateText.text = "분실물 정보 등록 준비 완료"

                    dlg.dismiss()
                }
            }
            cancelBtn.setOnClickListener {
                dlg.dismiss()
            }
        }

        dlg.show()
    }

    private fun isEmptyTextExist() : Boolean{
        if(binding.lostName.text.isEmpty() || binding.findPos.text.isEmpty()) return true
        else return false
    }

    private fun clearText(){
        binding.lostName.text.clear()
        binding.findPos.text.clear()
    }
}