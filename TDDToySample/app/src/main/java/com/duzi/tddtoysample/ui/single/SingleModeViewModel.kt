package com.duzi.tddtoysample.ui.single

import androidx.lifecycle.*
import com.duzi.tddtoysample.domain.usecase.GenerateQuizUseCase
import com.duzi.tddtoysample.result.process
import kotlinx.coroutines.launch

class SingleModeViewModel(
    private val generateQuizUseCase: GenerateQuizUseCase
) : ViewModel() {
    
    // 퀴즈 상태 관리
    sealed class QuizState {
        object UP : QuizState()
        object DOWN : QuizState()
        object BINGO : QuizState()
    }

    // 시도 상태
    private val _tryStatus = MutableLiveData<String>()
    val tryStatus: LiveData<String> = _tryStatus

    // 정답
    private val _answer = MutableLiveData<Int>()
    val answer: LiveData<Int> get() = _answer

    // 추측 값
    private val _guess = MutableLiveData<Int>()

    // 퀴즈 상태
    private val _quizState = MediatorLiveData<QuizState>().apply {
        addSource(_guess) { guess ->
            answer.value?.let { answer ->
                val result = checkState(answer, guess)
                value = result
            }
        }
    }
    val quizState: LiveData<QuizState> = _quizState
    private var tries: Int = 0

    fun generateAnswer() {
        viewModelScope.launch {
            generateQuizUseCase(Unit).process({
                _answer.postValue(it)
            }, {
                //error
            })
        }
    }

    fun submitAnswer(guess: Int) {
        _guess.value = guess
        tries++
    }

    private fun checkState(answer: Int, guess: Int): QuizState {
        return when {
            guess > answer -> QuizState.UP
            guess < answer -> QuizState.DOWN
            else -> {
                setTriesStatus()
                QuizState.BINGO
            }
        }
    }

    private fun setTriesStatus() {
        _tryStatus.postValue("총 ${tries}회 시도")
        tries = 0
    }

}