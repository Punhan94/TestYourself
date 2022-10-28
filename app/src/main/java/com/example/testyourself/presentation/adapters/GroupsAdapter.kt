package com.example.testyourself.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testyourself.R
import com.example.testyourself.domain.models.Group
import kotlinx.android.synthetic.main.group_item.view.*

class GroupsAdapter:RecyclerView.Adapter<GroupsAdapter.GroupsViewHolder>() {

    var onItemClick : ((Group)->Unit) ?= null
    inner class ItemClickListener{
        fun onItemClick(view: View, position: Int){
        }
    }

    inner class GroupsViewHolder (itemVIew:View):RecyclerView.ViewHolder(itemVIew)

    private val differCallBack = object :DiffUtil.ItemCallback<Group>(){
        override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean {
            return oldItem==newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
        return GroupsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.group_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
        differ.let { groups->
            holder.itemView.groupName.text = groups.currentList[position].groupName

            holder.itemView.setOnClickListener {
                onItemClick?.invoke(groups.currentList[position])
            }

        }
    }

    override fun getItemCount()= differ.currentList.size
}