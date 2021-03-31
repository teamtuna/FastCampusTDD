package com.duzi.tddtoysample.domain.usecase

import com.duzi.tddtoysample.domain.repository.AnswerGenerateRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetAnswersUseCase constructor(
    private val answerGenerateRepository: AnswerGenerateRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): UseCase<Unit, IntArray>(dispatcher) {
    override suspend fun execute(parameters: Unit): IntArray {
        return answerGenerateRepository.generateLessThanOrEqualToHundred()
    }
}