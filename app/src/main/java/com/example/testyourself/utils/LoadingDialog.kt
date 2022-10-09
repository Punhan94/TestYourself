package com.example.testyourself.utils

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.testyourself.R

class LoadingDialog(val fragment: Fragment) {
    private lateinit var isDialog:AlertDialog

    fun startLoading(){
        val inflater = fragment.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_item, null)
        val builder = AlertDialog.Builder(fragment.requireContext())
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
    }

    fun isDisMiss(){
        isDialog.dismiss()
    }

}