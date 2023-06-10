package com.example.lostku

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lostku.databinding.RowLostBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class LostRecyclerViewAdapter(options : FirebaseRecyclerOptions<LostData>)
    : FirebaseRecyclerAdapter<LostData,LostRecyclerViewAdapter.ViewHolder>(options){


    interface  OnItemClickListener{
        fun OnItemClick(position: Int, data:LostData)

    }

    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(val binding : RowLostBinding):RecyclerView.ViewHolder(binding.root){
        init{
            binding.deleteBtn.setOnClickListener{
                itemClickListener!!.OnItemClick(adapterPosition,getItem(adapterPosition))
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
        holder.binding.apply {
            name.text = model.name
            foundLoc.text = model.foundLoc
            havingLoc.text = model.havingLoc
            time.text = model.time
            photo.text = model.photo
        }
    }


}