package com.example.lostku

import android.content.Intent
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
                val intent1 = Intent(this@MainActivity, LostListActivity::class.java)
                startActivity(intent1)
            }

            FindBtn.setOnClickListener {
                Toast.makeText(this@MainActivity, "FindBtn!!!", Toast.LENGTH_SHORT).show()
                val intent2 = Intent(this@MainActivity, FindLostActivity::class.java)
                startActivity(intent2)

            }

            UploadBtn.setOnClickListener {
                Toast.makeText(this@MainActivity, "UploadBtn!!!", Toast.LENGTH_SHORT).show()
                val intent3 = Intent(this@MainActivity, UploadActivity::class.java)
                startActivity(intent3)
            }
        }
    }
}