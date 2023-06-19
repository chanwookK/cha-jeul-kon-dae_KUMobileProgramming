package com.example.lostku

import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lostku.databinding.RowKeywordBinding

class KeywordRecyclerViewAdapter(var items:ArrayList<KeywordData>): RecyclerView.Adapter<KeywordRecyclerViewAdapter.ViewHolder>() {
    var keyNum = 0;

    interface  OnItemClickListener{
        fun OnItemClick(position: Int)

    }

    var itemClickListener:OnItemClickListener?=null
    inner class ViewHolder(val binding: RowKeywordBinding) : RecyclerView.ViewHolder(binding.root) {
        //OnItemClick 함수를 호출하는 위치
        init {
            binding.keywordDeleteBtn.setOnClickListener {
                removeItem(adapterPosition)
                keyNum--
                itemClickListener!!.OnItemClick(adapterPosition)

            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowKeywordBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //변수에다가 각각의 값들을 매핑하고 리사이클뷰에 보여줌
        holder.binding.keywordText.text = items[position].keywordName

    }

    fun addItems(name:String){
        items.add(KeywordData(name))
        notifyItemInserted(items.size-1)

    }

    fun removeItem(pos:Int){
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }
}