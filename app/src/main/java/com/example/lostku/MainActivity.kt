package com.example.lostku

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Base64
import android.util.TypedValue
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lostku.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding :ActivityMainBinding
    lateinit var drawer: DrawerLayout
    var isDrawerOpen = false
    lateinit var pdb: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        drawer = binding.drawer
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        pdb = Firebase.database.getReference("PW")
        binding.apply {
            ListBtn.setOnClickListener {
                val intent1 = Intent(this@MainActivity, LostListActivity::class.java)
                startActivity(intent1)
            }

            FindBtn.setOnClickListener {
                val intent2 = Intent(this@MainActivity, FindLostActivity::class.java)
                startActivity(intent2)

            }

            UploadBtn.setOnClickListener {
                // Create a layout to hold the EditText
                val layout = FrameLayout(this@MainActivity)
                val pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250f, resources.displayMetrics).toInt() // DP를 Pixel로 변환
                val input = EditText(this@MainActivity).apply {
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
                val dialog = android.app.AlertDialog.Builder(this@MainActivity)
                    .setTitle("비밀번호 입력")
                    .setMessage("비밀번호를 입력하세요")
                    .setView(layout)
                    .setPositiveButton("확인") { dialog, _ ->
                        // 비밀번호를 처리하는 코드
                        val password = input.text.toString()
                        // 비밀번호 확인
                        pdb.get().addOnSuccessListener { dataSnapshot ->
                            var isPasswordCorrect = false

                            // Iterate over all child nodes of "비밀번호"
                            for (child in dataSnapshot.children) {
                                val savedPassword = child.value as? String

                                if (savedPassword != null) {
                                    val decodedPassword = String(Base64.decode(savedPassword, Base64.DEFAULT)).trim()

                                    // Check if the decoded password matches the user's password
                                    if (decodedPassword == password) {
                                        isPasswordCorrect = true
                                        break
                                    }
                                }
                            }

                            if (isPasswordCorrect) {
                                //deleteBtn 클릭했을 때 DB에서 삭제
                                val intent = Intent(this@MainActivity, UploadActivity::class.java)
                                intent.putExtra("PASSWORD", password)
                                setResult(Activity.RESULT_OK, intent)
                                startActivity(intent)
                            } else {
                                Toast.makeText(applicationContext, "비밀번호가 잘못되었습니다", Toast.LENGTH_SHORT).show()
                            }
                        }.addOnFailureListener {
                            Toast.makeText(applicationContext, "비밀번호 확인에 실패했습니다", Toast.LENGTH_SHORT).show()
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