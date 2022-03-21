package com.nkuppan.weatherapp.domain.model

data class Alert(
    val senderName: String,
    val start: Long,
    val end: Long,
    val event: String,
    val description: String? = null,
    val tags: List<String>? = null,
)