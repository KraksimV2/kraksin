package pl.edu.agh.cs.kraksim.api.factory

import org.springframework.stereotype.Component
import pl.edu.agh.cs.kraksim.core.MovementSimulationStrategy
import pl.edu.agh.cs.kraksim.core.Simulation
import pl.edu.agh.cs.kraksim.core.state.SimulationState
import pl.edu.agh.cs.kraksim.nagelCore.NagelMovementSimulationStrategy
import pl.edu.agh.cs.kraksim.nagelCore.NagelSimulation
import pl.edu.agh.cs.kraksim.nagelCore.state.NagelSimulationState
import pl.edu.agh.cs.kraksim.repository.entities.SimulationType
import pl.edu.agh.cs.kraksim.statistics.StatisticsManager
import pl.edu.agh.cs.kraksim.trafficLight.LightPhaseManager

@Component
class SimulationFactory {

    fun from(
        simulationType: SimulationType,
        simulationState: SimulationState,
        movementStrategy: MovementSimulationStrategy,
        lightPhaseManager: LightPhaseManager,
        statisticsManager: StatisticsManager
    ): Simulation = when (simulationType) {
        SimulationType.NAGEL_CORE -> createNagel(
            simulationState,
            movementStrategy,
            lightPhaseManager,
            statisticsManager
        )
    }

    private fun createNagel(
        simulationState: SimulationState,
        movementStrategy: MovementSimulationStrategy,
        lightPhaseManager: LightPhaseManager,
        statisticsManager: StatisticsManager
    ): NagelSimulation {
        require(simulationState is NagelSimulationState) { "Error creating NagelSimulation - simulation state expected to be NagelSimulationState, but was $simulationState" }
        require(movementStrategy is NagelMovementSimulationStrategy) { "Error creating NagelSimulation - movement strategy expected to be NagelMovementSimulationStrategy, but was $movementStrategy" }

        return NagelSimulation(
            simulationState, movementStrategy, lightPhaseManager, statisticsManager
        )
    }

}