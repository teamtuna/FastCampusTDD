package com.duzi.tddtoysample.ui.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duzi.tddtoysample.domain.usecase.GetAnswersUseCase
import com.duzi.tddtoysample.result.*
import kotlinx.coroutines.launch

class SingleModeViewModel(private val getAnswersUseCase: GetAnswersUseCase): ViewModel() {

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean> = _showProgress

    private val _showError = MutableLiveData<Boolean>()
    val showError: LiveData<Boolean> = _showError

    private val _answerStatus = MutableLiveData<String>()
    val answerStatus: LiveData<String> = _answerStatus

    var answers: IntArray = intArrayOf()
    var currentAnswerIndex = 0
    var currentAnswer = 0

    fun loadAnswers() {
        setLoadState()
        viewModelScope.launch {
            when (val result = getAnswersUseCase(Unit)) {
                is Result.Success -> {
                    val resultAnswers = result.successOr(intArrayOf())
                    answers = resultAnswers
                    setLoadStateCompleted()
                }
                is Result.Error -> { setErrorState() }
                else -> {}
            }
        }
    }

    private fun setLoadState() {
        _showProgress.postValue(true)
        _showError.postValue(false)
        _answerStatus.postValue("")
    }

    private fun setLoadStateCompleted() {
        _showProgress.postValue(false)
        _showError.postValue(false)
        _answerStatus.postValue("")
    }

    private fun setErrorState() {
        _showError.postValue(true)
    }

    fun selectAnswer(index: Int) {
        currentAnswerIndex = index

        if (index <= answers.size - 1)
            currentAnswer = answers[index]
    }

    fun submitAnswer(guess: Int) {
        if (guess > currentAnswer) {
            _answerStatus.postValue("입력한 값이 정답보다 큽니다.")
        }
    }
}