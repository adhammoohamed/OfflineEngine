package com.example.offlineengine.presentation.features.select_category.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.offlineengine.domain.usecase.UserSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectCategoryViewModel @Inject constructor(
    private val userSettingsUseCase: UserSettingsUseCase
) : ViewModel() {
    val categories =
        listOf("business", "entertainment", "general", "health", "science", "technology")
    private var _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory = _selectedCategory

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun saveUserCategory() {
        viewModelScope.launch {
            userSettingsUseCase.updateCategory(selectedCategory.value!!)
        }
    }
}


