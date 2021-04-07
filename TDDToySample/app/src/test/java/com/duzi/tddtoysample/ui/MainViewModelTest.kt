package com.duzi.tddtoysample.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.duzi.tddtoysample.MainCoroutineRule
import com.duzi.tddtoysample.ui.single.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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
        mainViewModel = MainViewModel()
    }

    @Test
    fun `싱글 플레이 모드 선택`() = runBlocking {
        mainViewModel.onClickSinglePlay()
        mainViewModel.navigationEvent.observeForever(Observer {
            Assert.assertEquals(it, MainViewModel.NavigationEvent.GoToSinglePlay)
        })
    }

}