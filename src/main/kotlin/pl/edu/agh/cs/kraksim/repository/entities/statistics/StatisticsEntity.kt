package pl.edu.agh.cs.kraksim.repository.entities.statistics

import pl.edu.agh.cs.kraksim.common.AverageSpeed
import pl.edu.agh.cs.kraksim.common.Density
import pl.edu.agh.cs.kraksim.common.FlowRatio
import pl.edu.agh.cs.kraksim.common.RoadId
import pl.edu.agh.cs.kraksim.repository.MapToStringConverter
import pl.edu.agh.cs.kraksim.repository.entities.SimulationEntity
import javax.persistence.*

@Entity
class StatisticsEntity(
    @ManyToOne
    val simulationEntity: SimulationEntity,
    val turn: Long,
    @OneToOne(cascade = [CascadeType.ALL])
    val currentStatisticsValues: StatisticsValuesEntity,
    @OneToOne(cascade = [CascadeType.ALL])
    val totalStatisticsValues: StatisticsValuesEntity,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
}

/**
 * Intellij is retarded, spent 3 hours searching internet just to find out
 * that mapping Map by converter works as mapping List, but fo some reason inspection
 * is shown here
 */
@Suppress("JpaAttributeTypeInspection")
@Entity
class StatisticsValuesEntity(
    @Embedded
    val speedStatistics: SpeedStatisticsEntity,
    @Column
    @Convert(converter = MapToStringConverter::class)
    val density: Map<RoadId, Density>,
    @Column
    @Convert(converter = MapToStringConverter::class)
    val roadFlowRatio: Map<RoadId, FlowRatio>
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
}

@Suppress("JpaAttributeTypeInspection") // see explanation above
@Embeddable
class SpeedStatisticsEntity(
    val wholeMapAverageSpeed: AverageSpeed,
    @Column
    @Convert(converter = MapToStringConverter::class)
    val roadAverageSpeed: Map<RoadId, AverageSpeed>
)
