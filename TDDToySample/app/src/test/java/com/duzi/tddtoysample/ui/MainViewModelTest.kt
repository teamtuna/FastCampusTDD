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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupViewModel() {

    }

    @Test
    fun `싱글 서비스 선택`() = runBlocking{
        mainViewModel.onClickSinglePlay()

        mainViewModel.navigationEvent.observeForever(Observer {
            Assert.assertEquals(it, MainViewModel.NavigationEvent.GoToSinglePlay)
        })
    }

}