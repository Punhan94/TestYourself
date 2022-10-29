package com.example.testyourself.presentation.viewmodels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyourself.domain.models.Student
import com.example.testyourself.domain.models.UserProfile
import com.example.testyourself.domain.repositories.ExamApiRepository
import com.example.testyourself.domain.usecases.exam_api_usecase.*
import com.example.testyourself.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentHomeViewModel @Inject constructor(
    repository: ExamApiRepository
):ViewModel() {
    private val getUserProfileUseCase: GetUserProfileUseCase = GetUserProfileUseCase(repository)
    private val getAllUserProfileUseCase: GetAllUserProfileUseCase = GetAllUserProfileUseCase(repository)
    private val getAllStudentUseCase: GetAllStudentUseCase = GetAllStudentUseCase(repository)
    private val postNewUserProfileUseCase: PostNewUserProfileUseCase = PostNewUserProfileUseCase(repository)
    private val getEmailForStudentUseCase: GetEmailForStudentUseCase = GetEmailForStudentUseCase(repository)
    private val postStudentUseCase: PostStudentUseCase = PostStudentUseCase(repository)
    var userProfile : MutableLiveData<UserProfile> = MutableLiveData()
    private var newStudent : MutableLiveData<Student> = MutableLiveData()
    var allStudent : MutableLiveData<List<Student>> = MutableLiveData()
    private var onlyStudent:MutableLiveData<List<Student>> = MutableLiveData()
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

    private fun initFun() = viewModelScope.launch {
        getAllStudent()
        allUserProfile()
    }

    fun newUserProfile(studentId:Int)= viewModelScope.launch{
        newUserProfileAdd(studentId)
    }

    private suspend fun getOnlyStudentForEmail(studentName:String){
        val responseStudent = getEmailForStudentUseCase.invoke(studentName)
        val body=responseStudent.body()
        if (responseStudent.isSuccessful) {
            body?.let {
                onlyStudent.postValue(it)

            }
        }
    }


    private suspend fun getAllStudent(){
        val getAllStudent = getAllStudentUseCase.invoke()
        if (getAllStudent.isSuccessful){
            allStudent.postValue(getAllStudent.body())
        }
    }

    private suspend fun allUserProfile(){
        val getAllUserProfile = getAllUserProfileUseCase.invoke()
        if (getAllUserProfile.isSuccessful){
            usersProfile.postValue(getAllUserProfile.body())
        }
    }

    private suspend fun getAboutUserProfile(studentId:Int){
        val getUserProfile = getUserProfileUseCase.invoke(studentId)
        if (getUserProfile.isSuccessful) {
            userProfile.postValue(getUserProfile.body())
        }
    }

    private suspend fun newStudentPOST(student: Student){
        val newStudentRepo = postStudentUseCase.invoke(student)
        if (newStudentRepo.isSuccessful){
            newStudent.postValue(newStudentRepo.body())
            newStudentRepo.body()?.id?.let {
                newUserProfileAdd(it)
                Constant.STUDENT_ID=it
            }
        }
    }

    private suspend fun newUserProfileAdd(studentId:Int){
        postNewUserProfileUseCase.invoke(
            UserProfile(
                student = studentId
                )
            )
    }

}