package pl.edu.agh.cs.kraksim.common

import pl.edu.agh.cs.kraksim.common.random.RandomProvider
import pl.edu.agh.cs.kraksim.core.movementStrategyUseCase.nagel.NagelMovementSimulationStrategy

fun testNagelMovementSimulationStrategy(randomProvider: RandomProvider = MockRandomProvider()) =
    NagelMovementSimulationStrategy(randomProvider)
