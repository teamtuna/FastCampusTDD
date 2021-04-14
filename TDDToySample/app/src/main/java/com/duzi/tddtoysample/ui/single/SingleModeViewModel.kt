package com.duzi.tddtoysample.ui.single

import androidx.lifecycle.*
import com.duzi.tddtoysample.domain.usecase.GenerateQuizUseCase
import com.duzi.tddtoysample.result.process
import kotlinx.coroutines.launch
import java.lang.Exception

class SingleModeViewModel(
    private val generateQuizUseCase: GenerateQuizUseCase
) : ViewModel() {
    
    // 퀴즈 상태 관리
    sealed class QuizState {
        object UP : QuizState()
        object DOWN : QuizState()
        class BINGO(val tries: Int = 0) : QuizState()
    }

    // 정답
    private val _answer = MutableLiveData<Int>()
    val answer: LiveData<Int> get() = _answer

    // 추측 값
    private val _guess = MutableLiveData<Int>()

    // 퀴즈 상태
    private val _quizState = quizStateZipLiveData(_answer, _guess)
    val quizState: LiveData<QuizState> = _quizState
    private var tries: Int = 0

    fun generateAnswer() {
        //tries = 0

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
                QuizState.BINGO(tries)
            }
        }
    }

    // https://medium.com/@gauravgyal/combine-results-from-multiple-async-requests-90b6b45978f7
    private fun quizStateZipLiveData(answer: LiveData<Int>, guess: LiveData<Int>): LiveData<QuizState> {
        return MediatorLiveData<QuizState>().apply {
            var lastAnswer: Int? = null
            var lastGuess: Int? = null

            fun update() {
                val localLastAnswer = lastAnswer
                val localLastGuess = lastGuess
                if (localLastAnswer != null && localLastGuess != null) {
                    this.value = checkState(localLastAnswer, localLastGuess)
                }
            }

            addSource(answer) {
                lastAnswer = it
                update()
            }
            addSource(guess) {
                lastGuess = it
                update()
            }
        }
    }

}
