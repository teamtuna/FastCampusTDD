package com.duzi.tddtoysample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(

) : ViewModel() {

    sealed class NavigationEvent {
        object GoToSinglePlay : NavigationEvent()
        object GoToMultiPlay : NavigationEvent()
    }

    private val _navigationEvent: MutableLiveData<NavigationEvent> = MutableLiveData()
    val navigationEvent: LiveData<NavigationEvent> get() = _navigationEvent


    fun onClickSinglePlay() {
        _navigationEvent.postValue(NavigationEvent.GoToSinglePlay)
    }

    fun onClickMultiPlay() {
        _navigationEvent.postValue(NavigationEvent.GoToMultiPlay)
    }


}