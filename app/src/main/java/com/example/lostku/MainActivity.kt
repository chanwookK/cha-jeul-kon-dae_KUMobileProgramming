package com.example.lostku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lostku.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding :ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        binding.apply {
            ListBtn.setOnClickListener {
                Toast.makeText(this@MainActivity, "ListBtn!!!", Toast.LENGTH_SHORT).show()

            }

            FindBtn.setOnClickListener {
                Toast.makeText(this@MainActivity, "FindBtn!!!", Toast.LENGTH_SHORT).show()
            }

            UploadBtn.setOnClickListener {
                Toast.makeText(this@MainActivity, "UploadBtn!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}