package com.example.testyourself.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testyourself.R
import com.example.testyourself.data.models.Exam
import kotlinx.android.synthetic.main.lesson_item.view.*

class ExamAdapter : RecyclerView.Adapter<ExamAdapter.ExamViewHolder>() {
    var onItemClick : ((Exam)->Unit) ?= null

    inner class ExamViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    inner class ItemClickListener{
        fun onItemClick(view: View, position: Int){

        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Exam>(){
        override fun areItemsTheSame(oldItem: Exam, newItem: Exam): Boolean {
            return oldItem.examId==newItem.examId
        }

        override fun areContentsTheSame(oldItem: Exam, newItem: Exam): Boolean {
            return oldItem==newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder {
        return ExamViewHolder(
            LayoutInflater.from(parent.context).inflate(
            R.layout.lesson_item, parent, false
        ))
    }

    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
        differ.let {lessons->
            holder.itemView.lesson_name_textView.text = lessons.currentList[position].examName.uppercase()

            holder.itemView.setOnClickListener {
                onItemClick?.invoke(lessons.currentList[position])
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

}