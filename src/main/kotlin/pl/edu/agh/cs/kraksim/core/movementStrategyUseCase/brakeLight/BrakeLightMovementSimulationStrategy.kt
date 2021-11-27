package pl.edu.agh.cs.kraksim.core.movementStrategyUseCase.brakeLight

import pl.edu.agh.cs.kraksim.common.adjacentPairs
import pl.edu.agh.cs.kraksim.common.random.RandomProvider
import pl.edu.agh.cs.kraksim.core.movementStrategyUseCase.nagel.NagelMovementSimulationStrategy
import pl.edu.agh.cs.kraksim.core.movementStrategyUseCase.nagel.state.NagelCar
import pl.edu.agh.cs.kraksim.core.movementStrategyUseCase.nagel.state.NagelLane
import pl.edu.agh.cs.kraksim.core.movementStrategyUseCase.nagel.state.NagelSimulationState
import pl.edu.agh.cs.kraksim.core.state.SimulationState
import kotlin.math.min

class BrakeLightMovementSimulationStrategy(
    random: RandomProvider,
    maxVelocity: Int = 6,
    private val threshold: Int,
    val breakLightReactionProbability: Double,
    val accelerationDelayProbability: Double,
    val defaultProbability: Double
) : NagelMovementSimulationStrategy(random, maxVelocity) {
    override fun step(state: SimulationState) {
        brakeLightPhase(state as NagelSimulationState)
        super.step(state)
    }

    private fun brakeLightPhase(state: NagelSimulationState) {
        state.lanes.filter { it.containsCar() }
            .forEach { lane -> setBLParameter(lane) }
    }

    override fun slowCar(car: NagelCar, freeSpaceInCarPath: Int) {
        if (car.velocity > freeSpaceInCarPath) {
            car.velocity = freeSpaceInCarPath
            car.brakeLightOn = true
        }
    }

    override fun randomization(state: NagelSimulationState) {
        state.cars
            .forEach { car ->
                val shouldSlowDown = car.velocity > 0 && random.drawWhetherShouldSlowDown(car)
                if (shouldSlowDown) {
                    car.velocity -= 1
                    car.brakeLightOn = true
                }
            }
    }

    private fun setBLParameter(lane: NagelLane) {
        setBLAllCarsButLast(lane.cars)
        setBLLastCar(lane.cars.last())
    }

    private fun setBLAllCarsButLast(cars: MutableList<NagelCar>) {
        cars.adjacentPairs().forEach { (car, carInFront) ->
            val ts = min(car.velocity, threshold)
            val timeToReachNext = car.distanceFrom(carInFront) / car.velocity
            val probability = if (carInFront.brakeLightOn!! && timeToReachNext > ts) breakLightReactionProbability else if (car.velocity == 0) accelerationDelayProbability else defaultProbability
            random.probabilityMap += (car to probability)
        }
    }

    private fun setBLLastCar(car: NagelCar){
        val distanceToMoveOnCurrentLane = min(car.distanceFromEndOfLane, car.velocity)
        val distanceTotal = if (car.velocity > distanceToMoveOnCurrentLane) car.gps.getTargetLaneInNextRoad(this::getLane).getFreeSpaceInFront() + distanceToMoveOnCurrentLane else distanceToMoveOnCurrentLane
        val ts = min(car.velocity, threshold)
        val th = distanceTotal / car.velocity
        val carOnNextRoad = car.gps.getTargetLaneInNextRoad(this::getLane).cars[0] as NagelCar
        val probability = if (carOnNextRoad.brakeLightOn!! && th > ts) breakLightReactionProbability else if (car.velocity == 0) accelerationDelayProbability else defaultProbability
        random.probabilityMap += (car to probability)
    }

}