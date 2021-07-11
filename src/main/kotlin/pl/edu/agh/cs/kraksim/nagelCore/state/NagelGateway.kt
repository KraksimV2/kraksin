package pl.edu.agh.cs.kraksim.nagelCore.state

import pl.edu.agh.cs.kraksim.common.RoadId
import pl.edu.agh.cs.kraksim.core.state.Gateway

class NagelGateway(
    override val id: Long,
    override val endingRoads: List<NagelRoad>,
    startingRoads: List<NagelRoad>,
) : NagelRoadNode, Gateway {

    override val finishedCars: ArrayList<NagelCar> = ArrayList()
    override val startingRoads: Map<RoadId, NagelRoad> = startingRoads.associateBy { it.id }

    init {
        endingRoads.forEach { it.end = this }
    }

    fun addFinishedCar(car: NagelCar) {
        finishedCars.add(car)
        car.moveToLane(null)
    }

    override fun toString(): String {
        return "NagelGateway(id=$id, finishedCars=$finishedCars)"
    }
}
