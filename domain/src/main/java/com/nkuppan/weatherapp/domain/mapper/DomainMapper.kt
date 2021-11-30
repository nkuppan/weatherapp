package com.nkuppan.weatherapp.domain.mapper

/**
 * This abstraction will handle model mapping between service layer and business layers
 */
interface DomainMapper<in DataTransferObject, DomainObject> {

    fun dtoToDomainModel(dtoObject: DataTransferObject): DomainObject
}