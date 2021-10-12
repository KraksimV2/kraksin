package pl.edu.agh.cs.kraksim.controller.mappers.statistics

import org.mapstruct.Context
import org.mapstruct.Mapper
import pl.edu.agh.cs.kraksim.common.CycleAvoidingMappingContext
import pl.edu.agh.cs.kraksim.controller.dto.statistics.SpeedStatisticsDTO
import pl.edu.agh.cs.kraksim.statistics.domain.SpeedStatisticsEntity

@Mapper
interface SpeedStatisticsMapper {
    fun convertToDto(speedStatisticsEntity: SpeedStatisticsEntity, @Context context: CycleAvoidingMappingContext = CycleAvoidingMappingContext()): SpeedStatisticsDTO
    fun convertToEntity(speedStatisticsDTO: SpeedStatisticsDTO, @Context context: CycleAvoidingMappingContext = CycleAvoidingMappingContext()): SpeedStatisticsEntity
}
