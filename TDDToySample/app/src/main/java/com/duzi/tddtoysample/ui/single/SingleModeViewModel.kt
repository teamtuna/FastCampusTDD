package com.duzi.tddtoysample.ui.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duzi.tddtoysample.domain.usecase.GenerateQuizUseCase
import com.duzi.tddtoysample.domain.usecase.GetAnswersUseCase
import com.duzi.tddtoysample.result.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SingleModeViewModel(
    private val getAnswersUseCase: GetAnswersUseCase,
    private val generateQuizUseCase: GenerateQuizUseCase
) : ViewModel() {

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean> = _showProgress

    private val _showError = MutableLiveData<Boolean>()
    val showError: LiveData<Boolean> = _showError

    private val _answerStatus = MutableLiveData<String>()
    val answerStatus: LiveData<String> = _answerStatus

    private val _triesStatus = MutableLiveData<String>()
    val triesStatus: LiveData<String> = _triesStatus

    var answers: IntArray = intArrayOf()
    var currentAnswerIndex = 0
    var currentAnswer = 0
    var tries: Int = 0

    fun loadAnswers() {
        setLoadState()
        viewModelScope.launch(Dispatchers.IO) {
            getAnswersUseCase(Unit).process({
                answers = it
                setLoadStateCompleted()
            }, {
                setErrorState()
            })
        }
    }

    fun selectAnswer(index: Int) {
        currentAnswerIndex = index

        if (index <= answers.size - 1) {
            currentAnswer = answers[index]
        } else {
            throw Exception("index error")
        }
    }

    fun submitAnswer(guess: Int) {
        tries++
        when {
            guess > currentAnswer -> _answerStatus.postValue("입력한 값이 정답보다 큽니다.")
            guess < currentAnswer -> _answerStatus.postValue("입력한 값이 정답보다 작습니다.")
            else -> {
                _answerStatus.postValue("정답!")
                setTriesStatus()
            }
        }
    }

    private fun setLoadState() {
        _showProgress.postValue(true)
        _showError.postValue(false)
        _answerStatus.postValue("")
        _triesStatus.postValue("")
    }

    private fun setLoadStateCompleted() {
        _showProgress.postValue(false)
        _showError.postValue(false)
        _answerStatus.postValue("")
        _triesStatus.postValue("")
    }

    private fun setErrorState() {
        _showProgress.postValue(false)
        _showError.postValue(true)
        _answerStatus.postValue("")
        _triesStatus.postValue("")
    }

    private fun setTriesStatus() {
        _triesStatus.postValue("총 ${tries}회 시도")
        tries = 0
    }

    private val _answer = MutableLiveData<Int>()
    val answer: LiveData<Int> get() = _answer

    fun generateAnswer() {
        viewModelScope.launch {
            generateQuizUseCase(Unit).process({
                _answer.postValue(it)
            }, {
                //error
            })
        }
    }
}