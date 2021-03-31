package com.duzi.tddtoysample.domain.usecase

import com.duzi.tddtoysample.domain.repository.AnswerGenerateRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GenerateQuizUseCase constructor(
    private val answerGenerateRepository: AnswerGenerateRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UseCase<Unit, Int>(dispatcher) {
    override suspend fun execute(parameters: Unit): Int {
        return answerGenerateRepository.generateQuiz()
    }
}