package com.kampus.praktikumpemob

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: AdviceRepository
) : ViewModel() {

    val advice: MutableStateFlow<AdviceRespon> = MutableStateFlow(adviceRespon)
    init = getAdvice

    fun getAdvice() {
        viewModelScope.launch {
            advice.value = repository.getAdvice()
        }
    }
}