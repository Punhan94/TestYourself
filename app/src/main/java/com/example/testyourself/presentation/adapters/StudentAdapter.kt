package com.example.testyourself.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testyourself.R
import com.example.testyourself.domain.models.UserProfile
import kotlinx.android.synthetic.main.student_item.view.*

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentAdapterViewHolder>() {
    var onItemClick : ((UserProfile)->Unit) ?= null

    inner class StudentAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    inner class ItemClickListener{
        fun onItemClick(view: View, position: Int){

        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<UserProfile>(){
        override fun areItemsTheSame(oldItem: UserProfile, newItem: UserProfile): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: UserProfile, newItem: UserProfile): Boolean {
            return oldItem==newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAdapterViewHolder {
        return StudentAdapterViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.student_item, parent, false
                ))
    }

    override fun onBindViewHolder(holder: StudentAdapterViewHolder, position: Int) {
        differ.let {student->
            holder.itemView.studentEmail_textView.text =
                    student.currentList[position].lastName + " " + student.currentList[position].firstName
            Glide.with(holder.itemView)
                .load(student.currentList[position].image)
                .into(holder.itemView.student_img)

            holder.itemView.setOnClickListener {
                onItemClick?.invoke(student.currentList[position])
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

}