package com.example.testyourself.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyourself.domain.models.Student
import com.example.testyourself.domain.models.UserProfile
import com.example.testyourself.domain.repositories.ExamApiRepository
import com.example.testyourself.domain.usecases.exam_api_usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentHomeViewModel @Inject constructor(
    private val repository: ExamApiRepository
):ViewModel() {
    private val getUserProfileUseCase: GetUserProfileUseCase = GetUserProfileUseCase(repository)
    private val getAllUserProfileUseCase: GetAllUserProfileUseCase = GetAllUserProfileUseCase(repository)
    private val getAllStudentUseCase: GetAllStudentUseCase = GetAllStudentUseCase(repository)
    private val postNewUserProfileUseCase: PostNewUserProfileUseCase = PostNewUserProfileUseCase(repository)
    private val getEmailForStudentUseCase: GetEmailForStudentUseCase = GetEmailForStudentUseCase(repository)
    private val postStudentUseCase: PostStudentUseCase = PostStudentUseCase(repository)
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
        val responseStudent = getEmailForStudentUseCase.getEmailForStudent(studentName)
        if(responseStudent.isSuccessful and (responseStudent.body() != null)) {
            onlyStudent.postValue(responseStudent.body())
        }
    }


    private suspend fun getAllStudent(){
        val getAllStudent = getAllStudentUseCase.getAllStudent()
        if (getAllStudent.isSuccessful){
            allStudent.postValue(getAllStudent.body())
        }
    }

    private suspend fun allUserProfile(){
        val getAllUserProfile = getAllUserProfileUseCase.getAllUserProfile()
        if (getAllUserProfile.isSuccessful){
            usersProfile.postValue(getAllUserProfile.body())
        }
    }

    private suspend fun getAboutUserProfile(studentId:Int){
        val getUserProfile = getUserProfileUseCase.getUserProfile(studentId)
        if (getUserProfile.isSuccessful) {
            userProfile.postValue(getUserProfile.body())
        }
    }

    private suspend fun newStudentPOST(student: Student){
        val newStudentRepo = postStudentUseCase.postStudent(student)
        if (newStudentRepo.isSuccessful){
            newStudent.postValue(newStudentRepo.body())
            newStudentRepo.body()?.id?.let { newUserProfileAdd(it.toInt()) }
        }
    }

    private suspend fun newUserProfileAdd(studentId:Int){
        postNewUserProfileUseCase.postNewUserProfile(
            UserProfile(
                student = studentId
                )
            )
    }

}