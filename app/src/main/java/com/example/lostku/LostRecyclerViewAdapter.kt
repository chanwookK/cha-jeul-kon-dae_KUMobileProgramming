package com.example.lostku

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.lostku.databinding.RowLostBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.net.URL


class LostRecyclerViewAdapter(options : FirebaseRecyclerOptions<LostData>)
    : FirebaseRecyclerAdapter<LostData,LostRecyclerViewAdapter.ViewHolder>(options){
    lateinit var rdb: DatabaseReference


    interface  OnItemClickListener{
        fun OnItemClick(position: Int, data:LostData)
        fun OnPhotoClick(position: Int, data:LostData)
    }

    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(val binding : RowLostBinding):RecyclerView.ViewHolder(binding.root){
        init{
            binding.deleteBtn.setOnClickListener{
                itemClickListener!!.OnItemClick(adapterPosition,getItem(adapterPosition))
            }
            binding.photo.setOnClickListener{
                itemClickListener!!.OnPhotoClick(adapterPosition,getItem(adapterPosition))
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):ViewHolder {
        val view = RowLostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        model: LostData
    ) {
        val storageRef = Firebase.storage.reference
        holder.binding.apply {
            name.text = model.name
            foundLoc.text = model.foundLoc
            havingLoc.text = model.havingLoc
            time.text = model.time
            //photo.text = model.photo
            val filename = "Lost_"+model.id.toString()+".png"
            val imageRef = storageRef.child("Lost/info/"+filename) // 이미지의 경로
            val uri = imageRef.toString() // URI를 문자열로 가져옴
            Glide.with(holder.itemView).load(uri.toUri()).into(photo)
//            Glide.with(holder.itemView).load(model.photo.toUri()).into(photo)
//            Glide.with(holder.itemView).load(URL(model.photo)).diskCacheStrategy(DiskCacheStrategy.ALL).into(photo)
//            Log.i("","password : "+model.password)
            Log.i("", "photo : "+model.photo)

//            val storageRef = Firebase.storage.reference
//            val imageName = "Lost_"+model.id.toString()+".png"
//            var imageRef = storageRef.child("Lost/info/"+imageName)
//            Log.i("","imageName : "+"Lost/info/"+imageName)
//            imageRef.downloadUrl.addOnSuccessListener { uri ->
//                Glide.with(holder.itemView).load(uri).into(photo)
////                photo.setImageURI(uri)
//            }.addOnFailureListener { exception ->
//                // 이미지 다운로드 URL을 가져오는 데 실패한 경우 호출됩니다.
//                Log.e("FirebaseStorage", "Failed to retrieve image URL: ${exception.message}")
//            }
        }
    }


}