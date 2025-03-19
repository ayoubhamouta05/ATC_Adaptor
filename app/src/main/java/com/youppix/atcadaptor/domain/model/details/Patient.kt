package com.youppix.atcadaptor.domain.model.details

data class Patient(
    val patientId: String,
    val name: String,
    val age: Int,
    val weight: Double,
    val height: Double,
    val gender: String,
    val race: String
)
