package com.example.testyourself.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testyourself.data.repository.ApiRepository

class HomeViewModelProviderFactory(
    val newRepository:ApiRepository
) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserHomeViewModel(newRepository) as T
    }
}