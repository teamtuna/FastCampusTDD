package com.duzi.tddtoysample.temp.console

import com.duzi.tddtoysample.temp.PositiveIntegerGeneratorStub
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AppModelTest {

    @Test
    fun `초기화 테스트`() {

        // TODO ViewModel Test
        val sut = AppModel(PositiveIntegerGeneratorStub(intArrayOf(50)))
        val actual = sut.isCompleted()
        assertFalse(actual)
    }

    @Test
    fun `싱글 플레이어 모드에서 입력한 값이 정답보다 값이 작음`() {
        val answer = 50
        val guess = 40

        // TODO ViewModel Test
        val sut = AppModel(PositiveIntegerGeneratorStub(intArrayOf(answer)))
        sut.processInput(guess)

        val actual = sut.flushOutput()

    }

    @Test
    fun `싱글 플레이어 모드에서 입력한 값이 정답보다 값이 큼`() {
        val answer = 50
        val guess = 60

        // TODO ViewModel Test
        val sut = AppModel(PositiveIntegerGeneratorStub(intArrayOf(answer)))
        sut.processInput(guess)

        val actual = sut.flushOutput()

    }

    @Test
    fun `싱글 플레이어 모드에서 입력한 값이 정답보다 값이 같음`() {
        val answer = 50
        val guess = 50

        // TODO ViewModel Test
        val sut = AppModel(PositiveIntegerGeneratorStub(intArrayOf(answer)))
        sut.processInput(guess)

        val actual = sut.flushOutput()

    }

    @Test
    fun `싱글 플레이어 모드에서 사용자가 몇번 추측했는지 테스트`() {
        val answer = 50
        val guess = 50

        val failCount = 10

        // TODO ViewModel Test
        val sut = AppModel(PositiveIntegerGeneratorStub(intArrayOf(answer)))
        for (i in 0 until failCount) {
            sut.processInput(guess)
        }

        val actual = sut.flushOutput()

        //  actual == failCount + 1 이 나오는지 확인

    }

    @Test
    fun `싱글 플레이어 모드에서 각 게임마다 새로운 답을 만들어내는지 테스트`() {
        val answerArray = intArrayOf(1, 10, 100)

        // TODO ViewModel Test
        val sut = AppModel(PositiveIntegerGeneratorStub(answerArray))
        answerArray.forEach { i ->
            sut.processInput(i)
            val actual = sut.flushOutput()
        }
    }

    @Test
    fun `멀티 플레이어 모드에서 플레이어 여러명 입력 받기`() {

        // TODO ViewModel Test
        val sut = AppModel(PositiveIntegerGeneratorStub(intArrayOf(50)))
        sut.processInput(arrayOf("Bar", "Foo"))
        val actual = sut.flushOutput()
    }

    @Test
    fun `멀티 플레이어 모드에서 플레이어 차례가 재대로 돌아오는지 확인`() {

        // TODO ViewModel Test
        val sut = AppModel(PositiveIntegerGeneratorStub(intArrayOf(50)))
        sut.processInput(arrayOf("Bar", "Foo"))

        // TODO  1번 순서, 2번 순서, 다시 1번

        val actual = sut.flushOutput()
    }

    @Test
    fun `멀티 플레이어 모드에서 플레이어가 입력한 값이 정답보다 작다`() {

        // TODO ViewModel Test
        val sut = AppModel(PositiveIntegerGeneratorStub(intArrayOf(50)))
        sut.processInput(arrayOf("Bar", "Foo"))

        // TODO  싱글 플레이어 모드와는 다르게 마지막으로 입력한 플레이어의 이름과 함께 값이 작은지 표시해준다.

        val actual = sut.flushOutput()
    }

    @Test
    fun `멀티 플레이어 모드에서 플레이어가 입력한 값이 정답`() {

        // TODO ViewModel Test
        val sut = AppModel(PositiveIntegerGeneratorStub(intArrayOf(50)))
        sut.processInput(arrayOf("Bar", "Foo"))

        // TODO  싱글 플레이어 모드와는 다르게 마지막으로 입력한 플레이어의 이름과 함께 정답 표시.

        val actual = sut.flushOutput()
    }
}