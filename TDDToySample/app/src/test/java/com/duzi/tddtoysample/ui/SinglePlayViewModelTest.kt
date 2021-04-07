package com.duzi.tddtoysample.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.duzi.tddtoysample.LiveDataTestUtil
import com.duzi.tddtoysample.MainCoroutineRule
import com.duzi.tddtoysample.domain.repository.AnswerGenerateRepository
import com.duzi.tddtoysample.domain.usecase.GenerateQuizUseCase
import com.duzi.tddtoysample.ui.single.SingleModeViewModel
import com.nhaarman.mockitokotlin2.mockingDetails
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class SinglePlayViewModelTest {

    private lateinit var singleModeViewModel: SingleModeViewModel

    @Mock
    private lateinit var answerGenerateRepository: AnswerGenerateRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        answerGenerateRepository = mock(AnswerGenerateRepository::class.java)

        singleModeViewModel = SingleModeViewModel(
            GenerateQuizUseCase(answerGenerateRepository)
        )
    }

    @Test
    fun `생성된 값과 정답의 일치 확인`() {

        //given
        whenever(answerGenerateRepository.generateQuiz())
                .thenReturn(60)

        //when
        singleModeViewModel.generateAnswer()
        verify(answerGenerateRepository, times(1)).generateQuiz()
        val expected = 60

        //then
        val answer = LiveDataTestUtil.getValue(singleModeViewModel.answer)
        Assert.assertEquals(expected, answer)
    }

    @Test
    fun `정답을 입력하고 입력한 값이 정답보다 높으면 높다고 출력 함`() {
        //given
        whenever(answerGenerateRepository.generateQuiz())
                .thenReturn(70)

        singleModeViewModel.generateAnswer()
        verify(answerGenerateRepository, times(1)).generateQuiz()
        println(mockingDetails(answerGenerateRepository).printInvocations())

        val guess = 73

        //when
        singleModeViewModel.submitAnswer(guess)

        //then
        val quizState = LiveDataTestUtil.getValue(singleModeViewModel.quizState)
        Assert.assertEquals(SingleModeViewModel.QuizState.UP, quizState)
    }

    @Test
    fun `정답을 입력하고 입력한 값이 정답보다 낮으면 낮다고 출력 함`() {
        //given
        whenever(answerGenerateRepository.generateQuiz())
                .thenReturn(70)

        singleModeViewModel.generateAnswer()
        verify(answerGenerateRepository, times(1)).generateQuiz()
        println(mockingDetails(answerGenerateRepository).printInvocations())

        val guess = 40

        //when
        singleModeViewModel.submitAnswer(guess)

        //then
        val quizState = LiveDataTestUtil.getValue(singleModeViewModel.quizState)
        Assert.assertEquals(SingleModeViewModel.QuizState.DOWN, quizState)
    }

    @Test
    fun `정답을 입력하고 입력한 값이 정답과 같으면 같다고 출력 함`() {
        //given
        whenever(answerGenerateRepository.generateQuiz())
            .thenReturn(70)

        singleModeViewModel.generateAnswer()
        verify(answerGenerateRepository, times(1)).generateQuiz()

        val guess = 70

        //when
        singleModeViewModel.submitAnswer(guess)

        //then
        val quizState = LiveDataTestUtil.getValue(singleModeViewModel.quizState)
        Assert.assertEquals(SingleModeViewModel.QuizState.BINGO, quizState)
    }

    @Test
    fun `정답을 맞추고 시도한 횟수와 정답임을 알려줌`() {
        //given
        whenever(answerGenerateRepository.generateQuiz())
                .thenReturn(70)

        singleModeViewModel.generateAnswer()
        verify(answerGenerateRepository, times(1)).generateQuiz()
        var guess = 50
        val expectedTryStatus = "총 4회 시도"

        //when
        singleModeViewModel.submitAnswer(guess)
        var quizState = LiveDataTestUtil.getValue(singleModeViewModel.quizState)
        Assert.assertEquals(SingleModeViewModel.QuizState.DOWN, quizState)

        guess = 60
        singleModeViewModel.submitAnswer(guess)
        quizState = LiveDataTestUtil.getValue(singleModeViewModel.quizState)
        Assert.assertEquals(SingleModeViewModel.QuizState.DOWN, quizState)

        guess = 80
        singleModeViewModel.submitAnswer(guess)
        quizState = LiveDataTestUtil.getValue(singleModeViewModel.quizState)
        Assert.assertEquals(SingleModeViewModel.QuizState.UP, quizState)

        guess = 70
        singleModeViewModel.submitAnswer(guess)
        quizState = LiveDataTestUtil.getValue(singleModeViewModel.quizState)
        Assert.assertEquals(SingleModeViewModel.QuizState.BINGO, quizState)

        //then
        val tryStatus = LiveDataTestUtil.getValue(singleModeViewModel.tryStatus)
        Assert.assertEquals(expectedTryStatus, tryStatus)
    }
}