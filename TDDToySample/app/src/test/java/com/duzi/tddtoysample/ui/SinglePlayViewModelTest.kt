package com.duzi.tddtoysample.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.duzi.tddtoysample.LiveDataTestUtil
import com.duzi.tddtoysample.MainCoroutineRule
import com.duzi.tddtoysample.data.data.source.local.fake.testIntArray
import com.duzi.tddtoysample.domain.repository.AnswerGenerateRepository
import com.duzi.tddtoysample.domain.usecase.GetAnswersUseCase
import com.duzi.tddtoysample.ui.single.SingleModeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


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
        singleModeViewModel = SingleModeViewModel(GetAnswersUseCase(answerGenerateRepository))
    }

    @Test
    fun `정답 리스트 로드 테스트`() = runBlockingTest {
        //mainCoroutineRule.pauseDispatcher()

        runBlocking {
            singleModeViewModel.loadAnswers()
            var isShowProgress = LiveDataTestUtil.getValue(singleModeViewModel.showProgress)
            if (isShowProgress != null) {
                Assert.assertTrue(isShowProgress)
            } else {
                throw Exception("isShowProgress is null")
            }
        }

        runBlocking {
            delay(2000)

            var isShowProgress = LiveDataTestUtil.getValue(singleModeViewModel.showProgress)
            if (isShowProgress != null) {
                Assert.assertFalse(isShowProgress)
            } else {
                throw Exception("isShowProgress is null")
            }
        }

        val answers = singleModeViewModel.answers
        Assert.assertEquals(testIntArray.size, answers.size)

        //mainCoroutineRule.resumeDispatcher()
    }

    @Test
    fun `첫번째 문제 세팅 테스트`() = runBlockingTest {

        runBlocking {
            singleModeViewModel.loadAnswers()
            var isShowProgress = LiveDataTestUtil.getValue(singleModeViewModel.showProgress)
            if (isShowProgress != null) {
                Assert.assertTrue(isShowProgress)
            } else {
                throw Exception("isShowProgress is null")
            }
        }

        runBlocking {
            delay(2000)

            var isShowProgress = LiveDataTestUtil.getValue(singleModeViewModel.showProgress)
            if (isShowProgress != null) {
                Assert.assertFalse(isShowProgress)
            } else {
                throw Exception("isShowProgress is null")
            }
        }


        singleModeViewModel.selectAnswer(0)

        Assert.assertEquals(0, singleModeViewModel.currentAnswerIndex)

        val answer = singleModeViewModel.currentAnswer

        Assert.assertEquals(99, answer)
    }

    @Test
    fun `첫번째 문제 정답 맞추기 시도 후 정답보다 높음 테스트`() {
        runBlocking {
            singleModeViewModel.loadAnswers()
            var isShowProgress = LiveDataTestUtil.getValue(singleModeViewModel.showProgress)
            if (isShowProgress != null) {
                Assert.assertTrue(isShowProgress)
            } else {
                throw Exception("isShowProgress is null")
            }
        }

        runBlocking {
            delay(2000)

            var isShowProgress = LiveDataTestUtil.getValue(singleModeViewModel.showProgress)
            if (isShowProgress != null) {
                Assert.assertFalse(isShowProgress)
            } else {
                throw Exception("isShowProgress is null")
            }
        }

        singleModeViewModel.selectAnswer(0)

        Assert.assertEquals(0, singleModeViewModel.currentAnswerIndex)

        val guess = 100
        singleModeViewModel.submitAnswer(guess)

        val answerStatus = LiveDataTestUtil.getValue(singleModeViewModel.answerStatus)
        Assert.assertEquals("입력한 값이 정답보다 큽니다.", answerStatus)
    }

    @Test
    fun `첫번째 문제 정답 맞추기 시도 후 정답보다 낮음 테스트`() {
        runBlocking {
            singleModeViewModel.loadAnswers()
            var isShowProgress = LiveDataTestUtil.getValue(singleModeViewModel.showProgress)
            if (isShowProgress != null) {
                Assert.assertTrue(isShowProgress)
            } else {
                throw Exception("isShowProgress is null")
            }
        }

        runBlocking {
            delay(2000)

            var isShowProgress = LiveDataTestUtil.getValue(singleModeViewModel.showProgress)
            if (isShowProgress != null) {
                Assert.assertFalse(isShowProgress)
            } else {
                throw Exception("isShowProgress is null")
            }
        }

        singleModeViewModel.selectAnswer(0)

        Assert.assertEquals(0, singleModeViewModel.currentAnswerIndex)

        val guess = 50
        singleModeViewModel.submitAnswer(guess)

        val answerStatus = LiveDataTestUtil.getValue(singleModeViewModel.answerStatus)
        Assert.assertEquals("입력한 값이 정답보다 작습니다.", answerStatus)
    }

    @Test
    fun `첫번째 문제 정답 맞추기 시도 후 정답과 일치 테스트`() {
        runBlocking {
            singleModeViewModel.loadAnswers()
            var isShowProgress = LiveDataTestUtil.getValue(singleModeViewModel.showProgress)
            if (isShowProgress != null) {
                Assert.assertTrue(isShowProgress)
            } else {
                throw Exception("isShowProgress is null")
            }
        }

        runBlocking {
            delay(2000)

            var isShowProgress = LiveDataTestUtil.getValue(singleModeViewModel.showProgress)
            if (isShowProgress != null) {
                Assert.assertFalse(isShowProgress)
            } else {
                throw Exception("isShowProgress is null")
            }
        }

        singleModeViewModel.selectAnswer(0)

        Assert.assertEquals(0, singleModeViewModel.currentAnswerIndex)

        val guess = 99
        singleModeViewModel.submitAnswer(guess)

        val answerStatus = LiveDataTestUtil.getValue(singleModeViewModel.answerStatus)
        Assert.assertEquals("정답!", answerStatus)
    }

    @Test
    fun `첫번째 문제 정답 일치할 경우 시도한 횟수 테스트`() {
        runBlocking {
            singleModeViewModel.loadAnswers()
            var isShowProgress = LiveDataTestUtil.getValue(singleModeViewModel.showProgress)
            if (isShowProgress != null) {
                Assert.assertTrue(isShowProgress)
            } else {
                throw Exception("isShowProgress is null")
            }
        }

        runBlocking {
            delay(2000)

            var isShowProgress = LiveDataTestUtil.getValue(singleModeViewModel.showProgress)
            if (isShowProgress != null) {
                Assert.assertFalse(isShowProgress)
            } else {
                throw Exception("isShowProgress is null")
            }
        }

        singleModeViewModel.selectAnswer(0)

        Assert.assertEquals(0, singleModeViewModel.currentAnswerIndex)

        var guess = 50
        singleModeViewModel.submitAnswer(guess)
        guess = 60
        singleModeViewModel.submitAnswer(guess)
        guess = 70
        singleModeViewModel.submitAnswer(guess)
        guess = 99
        singleModeViewModel.submitAnswer(guess)

        val answerStatus = LiveDataTestUtil.getValue(singleModeViewModel.answerStatus)
        Assert.assertEquals("정답!", answerStatus)

        val triesStatus = LiveDataTestUtil.getValue(singleModeViewModel.triesStatus)
        Assert.assertEquals("총 4회 시도", triesStatus)
    }

    @Test
    fun `두번째 문제 이동 테스트`() {
        runBlocking {
            singleModeViewModel.loadAnswers()
            var isShowProgress = LiveDataTestUtil.getValue(singleModeViewModel.showProgress)
            if (isShowProgress != null) {
                Assert.assertTrue(isShowProgress)
            } else {
                throw Exception("isShowProgress is null")
            }
        }

        runBlocking {
            delay(2000)

            var isShowProgress = LiveDataTestUtil.getValue(singleModeViewModel.showProgress)
            if (isShowProgress != null) {
                Assert.assertFalse(isShowProgress)
            } else {
                throw Exception("isShowProgress is null")
            }
        }

        // TODO  index 가 아닌 next method 호출
        singleModeViewModel.selectAnswer(1)
        Assert.assertEquals(1, singleModeViewModel.currentAnswerIndex)
    }

    internal class FakeAnswerGenerateRepositoryImpl: AnswerGenerateRepository {
        override fun generateLessThanOrEqualToHundred(): IntArray {
            return testIntArray
        }
    }
}