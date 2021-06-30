package pl.edu.agh.cs.kraksim.statistics

import de.vandermeer.asciitable.AsciiTable
import pl.edu.agh.cs.kraksim.common.*

data class StateStatistics(
    val simulationId: Long,
    val turn: Long,

    val currentStatisticsValues: StatisticsValues,
    val totalStatisticsValues: StatisticsValues,
) {
    override fun toString(): String {

        return AsciiTable().apply {
            addRule()
            addRow(null, "Simulation ID: $simulationId", null, "Turn: $turn")
            addRule()
            addRow(null, null, null, "Current Statistic Values")
            insertStatistics(currentStatisticsValues)
            addRow(null, null, null, "Total Statistic Values")
            insertStatistics(totalStatisticsValues)
        }.render()
    }
}

data class StatisticsValues(
    val speedStatistics: SpeedStatistics,
    val density: Map<RoadId, Density>,
    val roadFlowRatio: Map<RoadId, FlowRatio>
)

data class SpeedStatistics(
    val wholeMapAverageSpeed: AverageSpeed,
    val roadAverageSpeed: Map<RoadId, AverageSpeed>
)

data class RoadData(
    val id: RoadId,
    val carsNumber: Int,
    val surface: Int
)
