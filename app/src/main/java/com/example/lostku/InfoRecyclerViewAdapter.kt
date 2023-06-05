package com.example.lostku

import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lostku.databinding.RowInfoBinding

class InfoRecyclerViewAdapter (var items:ArrayList<InfoData>): RecyclerView.Adapter<InfoRecyclerViewAdapter.ViewHolder>() {



    inner class ViewHolder(val binding: RowInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        //OnItemClick 함수를 호출하는 위치
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowInfoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //변수에다가 각각의 값들을 매핑하고 리사이클뷰에 보여줌
        if(position == 0){
            holder.binding.union.setBackgroundResource(R.drawable.keyword_background)
            holder.binding.location.setBackgroundResource(R.drawable.keyword_background)
        }
        holder.binding.union.text = items[position].union
        holder.binding.location.text = items[position].location


    }
}