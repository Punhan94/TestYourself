package com.example.testyourself.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyourself.data.models.Student
import com.example.testyourself.data.models.UserProfile
import com.example.testyourself.data.repository.ApiRepository
import kotlinx.coroutines.launch

class StudentHomeViewModel(
    private val repository: ApiRepository
):ViewModel() {
    var userProfile : MutableLiveData<UserProfile> = MutableLiveData()
    var newStudent : MutableLiveData<Student> = MutableLiveData()
    var allStudent : MutableLiveData<List<Student>> = MutableLiveData()
    var onlyStudent:MutableLiveData<List<Student>> = MutableLiveData()
    var usersProfile:MutableLiveData<List<UserProfile>> = MutableLiveData()

    init {
        initFun()
    }



    fun getAboutUser (studentId:Int) = viewModelScope.launch {
        getAboutUserProfile(studentId)
    }

    fun studentName(studentName:String) = viewModelScope.launch {
        getOnlyStudentForEmail(studentName)
    }

    fun userStudent(student: Student) = viewModelScope.launch {
        newStudentPOST(student)
    }

    fun initFun() = viewModelScope.launch {
        getAllStudent()
        allUserProfile()
    }

    fun newUserProfile(studentId:Int)= viewModelScope.launch{
        newUserProfileAdd(studentId)
    }

    private suspend fun getOnlyStudentForEmail(studentName:String){
        val responseStudent = repository.getEmailForStudent(studentName)
        if(responseStudent.isSuccessful and (responseStudent.body() != null)) {
            onlyStudent.postValue(responseStudent.body())
        }
    }


    private suspend fun getAllStudent(){
        if (repository.getAllStudent().isSuccessful){
            allStudent.postValue(repository.getAllStudent().body())
        }
    }

    private suspend fun allUserProfile(){
        if (repository.getAllUserProfile().isSuccessful){
            usersProfile.postValue(repository.getAllUserProfile().body())
        }
    }

    private suspend fun getAboutUserProfile(studentId:Int){
        if (repository.getUserProfile(studentId).isSuccessful) {
            userProfile.postValue(repository.getUserProfile(studentId).body())
        }
    }

    private suspend fun newStudentPOST(student: Student){
        val newStudentRepo = repository.postStudent(student)
        if (newStudentRepo.isSuccessful){
            newStudent.postValue(newStudentRepo.body())
            newStudentRepo.body()?.id?.let { newUserProfileAdd(it.toInt()) }
        }
    }

    private suspend fun newUserProfileAdd(studentId:Int){
        repository.postNewUserProfile(
            UserProfile(
                student = studentId
                )
            )
    }

}