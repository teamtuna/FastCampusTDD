package com.duzi.tddtoysample.temp.console

import com.duzi.tddtoysample.temp.PositiveIntegerGenerator

// TODO  ViewModel
class AppModel(generator: PositiveIntegerGenerator) {

    // 다음 게임을 만들어내는 인터페이스
    interface Processor {
        fun run(input: Int): Processor
    }

    private var output: String? = null
    var completed = false
    var answer = generator.generateLessThanOrEqualToHundred()
    var tries = 0

    init {
        output = ""
    }

    fun isCompleted(): Boolean {
        return true
    }

    fun flushOutput(): String? {
        return output
    }

    // TODO  ViewModel method
    fun processInput(input: Int) {
        tries++

        // TODO  값을 비교하는 로직 어디에??
        val guess = input
        val result = when {
            guess < answer -> ""
            guess > answer -> ""
            else -> ""
        }
        output = result

    }

    // TODO  ViewModel method
    fun processInput(strings: Array<String>) {

    }
}