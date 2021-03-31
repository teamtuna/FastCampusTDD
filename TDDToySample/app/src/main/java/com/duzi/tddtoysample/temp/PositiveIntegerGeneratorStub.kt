package com.duzi.tddtoysample.temp

// TODO  Repository implementation
class PositiveIntegerGeneratorStub(private val numbers: IntArray): PositiveIntegerGenerator {

    private var index = 0

    override fun generateLessThanOrEqualToHundred(): Int {
        val number = numbers[index]
        index = (index + 1) % numbers.size
        return number
    }
}