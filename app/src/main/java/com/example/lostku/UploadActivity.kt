package com.example.lostku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.lostku.databinding.ActivityUploadBinding

class UploadActivity : AppCompatActivity() {
    lateinit var binding : ActivityUploadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }


    private fun submitInfo()
    {

    }

    private fun submitImg()
    {

    }

    private fun initLayout() {
        binding.apply {

            // 2023.05.28 사진 등록 부분은 맨 처음엔 안 보이도록. (사진 등록 후 노출)
            imageView.visibility = View.GONE

            submitInfoButton.setOnClickListener {
                // 분실물 정보 등록 버튼
                submitInfo()
            }
            submitImgButton.setOnClickListener {
                // 이미지 제출 버튼
                submitImg()
            }
            uploadButton.setOnClickListener {
                // 제출 버튼

                // 분실물의 모든 정보 등록한 경우

                // 정보 등록이 아직 안 끝난 경우
            }
        }
    }
}