package com.example.testyourself.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.example.testyourself.R
import com.example.testyourself.data.models.Lesson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_login_or_sign_in.view.*
import kotlinx.android.synthetic.main.lesson_item.view.*

class LessonsAdapter(private var context: Fragment):RecyclerView.Adapter<LessonsAdapter.LessonViewHolder>() {
    var onItemClick : ((Lesson)->Unit) ?= null

    inner class LessonViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    inner class ItemClickListener{
        fun onItemClick(view: View,position: Int){
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Lesson>(){
        override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem.lessonsName==newItem.lessonsName
        }

        override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem==newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        return LessonViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.lesson_item, parent, false
        ))
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        differ.let {lessons->
            holder.itemView.lesson_name_textView.text = lessons.currentList[position].lessonsName.uppercase()
            Glide.with(context)
                .load(lessons.currentList[position].lessonImage)
                .into(holder.itemView.img_lesson)
            //Picasso.get().load(lessons.currentList[position].lessonImage).into(holder.itemView.img_lesson)
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(lessons.currentList[position])
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

}