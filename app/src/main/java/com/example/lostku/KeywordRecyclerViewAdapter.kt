package com.example.lostku

import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lostku.databinding.RowKeywordBinding

class KeywordRecyclerViewAdapter(var items:ArrayList<KeywordData>): RecyclerView.Adapter<KeywordRecyclerViewAdapter.ViewHolder>() {

    interface OnItemClickListener {
        //어댑터에서 몇번쨰 무엇을 클릭했는지 정보를 넘겨줄 수 있음, 넘겨줄 수 있는 데이터들을 인자를 통해 전달 가능
        fun OnItemClick(data: KeywordData, i: Int)

    }

    var itemClickListener: OnItemClickListener? = null


    inner class ViewHolder(val binding: RowKeywordBinding) : RecyclerView.ViewHolder(binding.root) {
        //OnItemClick 함수를 호출하는 위치
        init {
            binding.keywordDeleteBtn.setOnClickListener {
                removeItem(adapterPosition)
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