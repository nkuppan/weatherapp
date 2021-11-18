package com.nkuppan.weatherapp.data.network.model

import com.nkuppan.weatherapp.data.network.response.CityDto
import com.nkuppan.weatherapp.domain.mapper.DomainMapper
import com.nkuppan.weatherapp.domain.model.City

class CityDtoMapper : DomainMapper<CityDto, City> {
    override fun mapToDomainModel(model: CityDto): City {
        return City(
            model.name,
            model.key,
            model.rank,
            model.country.name
        )
    }

    override fun mapFromDomainModel(domainModel: City): CityDto {
        TODO("Not yet implemented")
    }

    fun toDomainList(initial: List<CityDto>): List<City> {
        return initial.map { mapToDomainModel(it) }
    }
}