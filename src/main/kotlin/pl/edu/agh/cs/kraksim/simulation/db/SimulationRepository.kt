package pl.edu.agh.cs.kraksim.simulation.db

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.edu.agh.cs.kraksim.simulation.domain.SimulationEntity
import pl.edu.agh.cs.kraksim.simulation.domain.SimulationType

@Repository
interface SimulationRepository : JpaRepository<SimulationEntity, Long> {

    fun findAllBy(): List<BasicSimulationInfo>
}

interface BasicSimulationInfo {
    val id: Long
    val name: String
    val simulationType: SimulationType
    val mapEntity: MapEntityId
    val simulationStateEntities: List<SimulationStateEntityTurn>
    var finished: Boolean
}

interface MapEntityId {
    val id: Long
}

interface SimulationStateEntityTurn {
    val turn: Long
}
