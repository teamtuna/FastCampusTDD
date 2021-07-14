package com.duzi.tddtoysample.ui

import com.duzi.tddtoysample.InstantExecutorExtension
import com.duzi.tddtoysample.LiveDataTestUtil
import com.duzi.tddtoysample.MainCoroutineExtension
import com.duzi.tddtoysample.domain.repository.AnswerGenerateRepository
import com.duzi.tddtoysample.domain.usecase.GenerateQuizUseCase
import com.duzi.tddtoysample.ui.single.SingleModeViewModel
import com.nhaarman.mockitokotlin2.mockingDetails
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.mockito.Mock
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class SinglePlayViewModelTest {

    companion object {
        @JvmField
        @RegisterExtension
        val coroutineExtension = MainCoroutineExtension()
    }

    private lateinit var singleModeViewModel: SingleModeViewModel

    @Mock
    private lateinit var answerGenerateRepository: AnswerGenerateRepository

    @Before
    fun setUp() {
        /**
         * 아래 코드와 동일함.
         * answerGenerateRepository = mock(AnswerGenerateRepository::class.java)
         *
         * or
         *
         * @RunWith(MockitoJUnitRunner::class)
         */
        MockitoAnnotations.initMocks(this)

        singleModeViewModel = SingleModeViewModel(
            GenerateQuizUseCase(answerGenerateRepository)
        )
    }

    @Test
    fun `생성된 값과 정답의 일치 확인`() = coroutineExtension.runBlockingTest {

        //given
        whenever(answerGenerateRepository.generateQuiz())
                .thenReturn(60)

        //when
        singleModeViewModel.generateAnswer()
        coroutineExtension.advanceUntilIdle()
        println(mockingDetails(answerGenerateRepository).printInvocations())
        val expected = 60

        //then
        val answer = LiveDataTestUtil.getValue(singleModeViewModel.answer)
        Assert.assertEquals(expected, answer)

        verify(answerGenerateRepository, times(1)).generateQuiz()
    }

    /*@Test
    fun `정답을 입력하고 입력한 값이 정답보다 높으면 높다고 출력 함`() = coroutineExtension.runBlockingTest  {
        //given
        whenever(answerGenerateRepository.generateQuiz())
                .thenReturn(70)

        singleModeViewModel.generateAnswer()
        coroutineExtension.advanceUntilIdle()
        println(mockingDetails(answerGenerateRepository).printInvocations())

        val guess = 73

        //when
        singleModeViewModel.submitAnswer(guess)

        //then
        val quizState = LiveDataTestUtil.getValue(singleModeViewModel.quizState)
        Assert.assertTrue(quizState is SingleModeViewModel.QuizState.UP)

        verify(answerGenerateRepository, times(1)).generateQuiz()
    }

    @Test
    fun `정답을 입력하고 입력한 값이 정답보다 낮으면 낮다고 출력 함`() = coroutineExtension.runBlockingTest  {
        //given
        whenever(answerGenerateRepository.generateQuiz())
                .thenReturn(70)

        singleModeViewModel.generateAnswer()
        coroutineExtension.advanceUntilIdle()
        println(mockingDetails(answerGenerateRepository).printInvocations())

        val guess = 40

        //when
        singleModeViewModel.submitAnswer(guess)

        //then
        val quizState = LiveDataTestUtil.getValue(singleModeViewModel.quizState)
        Assert.assertTrue(quizState is SingleModeViewModel.QuizState.DOWN)

        verify(answerGenerateRepository, times(1)).generateQuiz()
    }

    @Test
    fun `정답을 입력하고 입력한 값이 정답과 같으면 같다고 출력 함`() = coroutineExtension.runBlockingTest{
        //given
        whenever(answerGenerateRepository.generateQuiz())
            .thenReturn(70)

        singleModeViewModel.generateAnswer()
        coroutineExtension.advanceUntilIdle()
        println(mockingDetails(answerGenerateRepository).printInvocations())

        val guess = 70

        //when
        singleModeViewModel.submitAnswer(guess)

        //then
        val quizState = LiveDataTestUtil.getValue(singleModeViewModel.quizState)
        Assert.assertTrue(quizState is SingleModeViewModel.QuizState.BINGO)

        verify(answerGenerateRepository, times(1)).generateQuiz()
    }

    @Test
    fun `정답을 맞추고 시도한 횟수와 정답임을 알려줌`() = coroutineExtension.runBlockingTest  {
        //given
        whenever(answerGenerateRepository.generateQuiz())
                .thenReturn(70)

        singleModeViewModel.generateAnswer()
        coroutineExtension.advanceUntilIdle()
        println(mockingDetails(answerGenerateRepository).printInvocations())

        var guess = 50
        val expectedTries = 4

        //when
        singleModeViewModel.submitAnswer(guess)
        var quizState = LiveDataTestUtil.getValue(singleModeViewModel.quizState)
        Assert.assertTrue(quizState is SingleModeViewModel.QuizState.DOWN)

        guess = 60
        singleModeViewModel.submitAnswer(guess)
        quizState = LiveDataTestUtil.getValue(singleModeViewModel.quizState)
        Assert.assertTrue(quizState is SingleModeViewModel.QuizState.DOWN)

        guess = 80
        singleModeViewModel.submitAnswer(guess)
        quizState = LiveDataTestUtil.getValue(singleModeViewModel.quizState)
        Assert.assertTrue(quizState is SingleModeViewModel.QuizState.UP)

        guess = 70
        singleModeViewModel.submitAnswer(guess)
        quizState = LiveDataTestUtil.getValue(singleModeViewModel.quizState)
        Assert.assertTrue(quizState is SingleModeViewModel.QuizState.BINGO)

        //then
        Assert.assertEquals(expectedTries, (quizState as SingleModeViewModel.QuizState.BINGO).tries)

        verify(answerGenerateRepository, times(1)).generateQuiz()
    }*/
}