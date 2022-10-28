package com.example.testyourself.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel:ViewModel() {

    protected val _studentID: MutableLiveData<Int> = MutableLiveData()
    val studentID: MutableLiveData<Int>
        get() = _studentID

    protected val _userID: MutableLiveData<Int> = MutableLiveData()
    val userID: MutableLiveData<Int>
        get() = _userID


}