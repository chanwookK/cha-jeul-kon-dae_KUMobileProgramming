package com.example.lostku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lostku.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding :ActivityMainBinding
    lateinit var drawer: DrawerLayout
    var isDrawerOpen = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        drawer = binding.drawer
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.actionmenu, menu)
        //menuInflater.inflate(R.menu.actionmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.keywordAlarmBtn ->{
                val i = Intent(this@MainActivity, KeywordActivity::class.java)
                startActivity(i)

            }
            R.id.infoBtn ->{
                val i = Intent(this@MainActivity, InfoActivity::class.java)
                startActivity(i)

            }
            R.id.drawer ->{
                isDrawerOpen =! isDrawerOpen
                if(isDrawerOpen)
                    drawer.openDrawer(Gravity.RIGHT)
                else
                    drawer.closeDrawer(Gravity.RIGHT)

            }
        }
        return super.onOptionsItemSelected(item)
    }
}