package com.yewo.composesimpletraining.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FoodListViewModel: ViewModel() {
    val foods: LiveData<List<String>> get() = _foods
    private val _foods: MutableLiveData<List<String>> = MutableLiveData(listOf())

    val textFieldState: LiveData<String> get() = _textFieldState
    private val _textFieldState = MutableLiveData("")

    fun onTextChanged(value: String) {
        _textFieldState.value = value
    }

    fun addFood(text: String) {
        _foods.value = mutableListOf<String>().apply {
            foods.value?.let {
                addAll(it)
            }
            add(text)
        }
        // clear textField
        _textFieldState.value = ""
    }
}