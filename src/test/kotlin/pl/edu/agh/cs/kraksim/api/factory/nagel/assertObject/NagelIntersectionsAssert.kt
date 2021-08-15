package pl.edu.agh.cs.kraksim.api.factory.nagel.assertObject

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import pl.edu.agh.cs.kraksim.nagelCore.state.NagelIntersection
import pl.edu.agh.cs.kraksim.repository.entities.RoadNodeEntity
import pl.edu.agh.cs.kraksim.repository.entities.RoadNodeType
import pl.edu.agh.cs.kraksim.repository.entities.TurnDirectionEntity

class NagelIntersectionsAssert(
        private val intersections: List<NagelIntersection>
) {
    fun assertTurningDirections(intersectionId: Long, directions: List<TurnDirectionEntity>): NagelIntersectionsAssert {
        val intersection = intersections.find { it.id == intersectionId }
        directions.forEach { directionEntity ->
            assertThat(
                    intersection?.directions?.find { direction ->
                        directionEntity.sourceLane.id == direction.from &&
                                directionEntity.destinationRoad.id == direction.to
            }).isNotNull
        }
        return this
    }
}
