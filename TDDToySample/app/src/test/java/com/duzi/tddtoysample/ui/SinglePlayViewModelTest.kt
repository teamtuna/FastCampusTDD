package com.duzi.tddtoysample.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.duzi.tddtoysample.LiveDataTestUtil
import com.duzi.tddtoysample.MainCoroutineRule
import com.duzi.tddtoysample.data.data.source.local.fake.testIntArray
import com.duzi.tddtoysample.domain.repository.AnswerGenerateRepository
import com.duzi.tddtoysample.domain.usecase.GenerateQuizUseCase
import com.duzi.tddtoysample.domain.usecase.GetAnswersUseCase
import com.duzi.tddtoysample.ui.single.SingleModeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*


@ExperimentalCoroutinesApi
class SinglePlayViewModelTest {

    private lateinit var singleModeViewModel: SingleModeViewModel

    private lateinit var answerGenerateRepository: AnswerGenerateRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupViewModel() {
        answerGenerateRepository = FakeAnswerGenerateRepositoryImpl()
        singleModeViewModel = SingleModeViewModel(
            GetAnswersUseCase(answerGenerateRepository),
            GenerateQuizUseCase(answerGenerateRepository)
        )
    }

    @Test
    fun `생성된 값과 정답의 일치 확인`() {

        //give
        val expected = 1

        //when
        singleModeViewModel.generateAnswer()

        //then
        singleModeViewModel.answer.observeForever {
            Assert.assertEquals(expected, it)
        }
    }

    @Test
    fun `생성된 값리스트와 정답의 일치 확인`() = runBlockingTest {

        //give

        //when

        //then
    }

    @Test
    fun `정답을 입력하고 입력한 값이 정답보다 높으면 높다고 출력 함`() {
        //given
        singleModeViewModel.loadAnswers()
        runBlocking { delay(100) }
        singleModeViewModel.selectAnswer(0)
        val guess = 100
        val expectedStatus = "입력한 값이 정답보다 큽니다."

        //when
        singleModeViewModel.submitAnswer(guess)

        //then
        singleModeViewModel.answerStatus.observeForever { answerStatus ->
            Assert.assertEquals(expectedStatus, answerStatus)
        }
    }

    @Test
    fun `정답을 입력하고 입력한 값이 정답보다 낮으면 낮다고 출력 함`() {
        //given
        singleModeViewModel.loadAnswers()
        runBlocking { delay(100) }
        singleModeViewModel.selectAnswer(0)
        val guess = 1
        val expectedStatus = "입력한 값이 정답보다 작습니다."

        //when
        singleModeViewModel.submitAnswer(guess)

        //then
        singleModeViewModel.answerStatus.observeForever { answerStatus ->
            Assert.assertEquals(expectedStatus, answerStatus)
        }
    }

    @Test
    fun `정답을 입력하고 입력한 값이 정답과 같으면 같다고 출력 함`() {
        //given
        singleModeViewModel.loadAnswers()
        runBlocking { delay(100) }
        singleModeViewModel.selectAnswer(0)
        val guess = 99
        val expectedStatus = "정답!"

        //when
        singleModeViewModel.submitAnswer(guess)

        //then
        singleModeViewModel.answerStatus.observeForever { answerStatus ->
            Assert.assertEquals(expectedStatus, answerStatus)
        }
    }

    @Test
    fun `정답을 맞추고 시도한 횟수와 정답임을 알려줌`() {
        //given
        singleModeViewModel.loadAnswers()
        runBlocking { delay(100) }
        singleModeViewModel.selectAnswer(0)
        var guess = 50
        val expectedAnswerStatus = "정답!"
        val expectedTryStatus = "총 4회 시도"

        //when
        singleModeViewModel.submitAnswer(guess)
        guess = 60
        singleModeViewModel.submitAnswer(guess)
        guess = 70
        singleModeViewModel.submitAnswer(guess)
        guess = 99
        singleModeViewModel.submitAnswer(guess)

        //then
        singleModeViewModel.answerStatus.observeForever { answerStatus ->
            Assert.assertEquals(expectedAnswerStatus, answerStatus)
        }

        singleModeViewModel.triesStatus.observeForever { tryStatus ->
            Assert.assertEquals(expectedTryStatus, tryStatus)
        }
    }


    internal class FakeAnswerGenerateRepositoryImpl : AnswerGenerateRepository {
        override fun generateLessThanOrEqualToHundred(): IntArray {
            return testIntArray
        }

        override fun generateQuiz(): Int {
            // Random 값을 줄꺼잖아요
            return 1
        }
    }
}