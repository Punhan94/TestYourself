package com.example.testyourself.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.testyourself.utils.Resource

object ObservableData {

    //create user
    var createUserLiveData = MutableLiveData<Resource<String>>()
    var createUserJobLiveData :MutableLiveData<Resource<String>>?= MutableLiveData<Resource<String>>()
    //login user
    var loginUserLiveData = MutableLiveData<Resource<String>>()
    var loginUserJobCheckLiveData :MutableLiveData<Resource<String>>?= MutableLiveData<Resource<String>>()
}