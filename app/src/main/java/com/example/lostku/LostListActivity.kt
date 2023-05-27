package com.example.lostku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.lostku.databinding.ActivityLostListBinding

class LostListActivity : AppCompatActivity() {
    lateinit var binding : ActivityLostListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLostListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        binding.apply {
            searchBtn.setOnClickListener{

            }
        }
    }


}