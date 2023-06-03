package com.example.lostku

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.BoringLayout
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.lostku.databinding.ActivityUploadBinding

class UploadActivity : AppCompatActivity() {
    lateinit var binding : ActivityUploadBinding

    var isInfoSubmitted : Boolean = false
    var isImgSubmitted : Boolean = false

    val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE

    val GALLERY_REQUEST_CODE = 1001

    val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(!it){
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    fun setStateText(isSetNow : Boolean = false){
        if(isSetNow)
            binding.stateText.text = "분실물 정보 등록 준비 완료!"
        else
            binding.stateText.text = "아직 분실물 등록 전입니다!"
    }

    fun setGalleyText(isSetNow : Boolean = false){
        if(isSetNow)
            binding.stateText.text = "분실물 이미지 등록 완료!"
        else
            binding.stateText.text = "아직 사진 등록 전입니다!"
    }

    private fun submitInfo()
    {
        var dlg = UploadDialog(this)
        dlg.show(this)
    }

    fun galleryAlertDlg(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("반드시 갤러리 접근 권한이 모두 허용되어야 합니다.")
            .setTitle("권한체크")
            .setPositiveButton("Ok"){
                    _, _ // 여기서 넘어오는 인자 2개는 안 쓸 것임.
                -> permissionLauncher.launch(permission)
            }
            .setNegativeButton("Cancel"){
                    dlg, _ -> dlg.dismiss()
            }

        val dlg = builder.create()
        dlg.show()
    }

    private fun imageAction(){
        when{
            ActivityCompat.checkSelfPermission(this,
                permission) == PackageManager.PERMISSION_GRANTED ->{
                submitImg()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(this,
                permission) ->{
                // 명시적으로 거부했을 때
                galleryAlertDlg()
            }
            else -> {
                // 맨 처음에 실행했을 때
                permissionLauncher.launch(permission)
            }
        }
    }

    private fun submitImg()
    {
        val galleyIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleyIntent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val selectedImage: Uri? = data.data
            // 선택한 이미지에 대한 처리를 수행합니다.
            binding.imageView.visibility = View.VISIBLE
            binding.imageView.setImageURI(selectedImage)
            isImgSubmitted = true
            setGalleyText(true)
        }
    }

    private fun initLayout() {
        isInfoSubmitted = false
        isImgSubmitted = false

        // "아직 분실물 등록 전입니다!"
        setStateText()

        // "아직 사진 등록 전입니다!"
        setGalleyText()

        binding.apply {

            // 2023.05.28 사진 등록 부분은 맨 처음엔 안 보이도록. (사진 등록 후 노출)
            imageView.visibility = View.GONE

            submitInfoButton.setOnClickListener {
                // 분실물 정보 등록 버튼
                submitInfo()
            }
            submitImgButton.setOnClickListener {
                // 이미지 제출 버튼
                imageAction()
            }
            uploadButton.setOnClickListener {

                // 정보 등록이 아직 안 끝난 경우
                if(!isInfoSubmitted) {
                    Toast.makeText(this@UploadActivity, "분실물 정보를 등록해주세요!", Toast.LENGTH_SHORT).show()
                }
                else if(!isImgSubmitted) {
                    Toast.makeText(this@UploadActivity, "이미지를 등록해주세요!", Toast.LENGTH_SHORT).show()
                }
                else{
                    // 분실물의 모든 정보 등록한 경우
                    Toast.makeText(this@UploadActivity, "분실물 등록 완료!!", Toast.LENGTH_SHORT).show()
                    val intent1 = Intent(this@UploadActivity, MainActivity::class.java)
                    startActivity(intent1)

                    // TODO : DB에 분실물 정보 넣기.
                }
            }
        }
    }
}