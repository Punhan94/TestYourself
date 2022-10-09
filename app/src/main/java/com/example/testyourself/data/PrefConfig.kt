package com.example.testyourself.data

import android.content.Context
import android.content.SharedPreferences




class PrefConfig {
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var con: Context
    var PRIVATEMODE:Int=0

    constructor(con:Context){
        this.con = con
        pref = con.getSharedPreferences(STUDENT_ID, PRIVATEMODE)
        editor = pref.edit()
    }
    companion object{
        val STUDENT_ID = "StudentId"
    }
    fun create(studentId:String){
        editor.putString(STUDENT_ID, studentId)
        editor.commit()
    }

}