package com.example.mededu.models

data class Annotation(
    val id: String = UUID.randomUUID().toString(),
    val position: Vector3,
    val note: String,
    val painLevel: PainLevel = PainLevel.NONE,
    val createdAt: Date = Date(),
    val structureIds: List<String> = emptyList(),
    val drawing: Bitmap? = null
) {
    enum class PainLevel {
        NONE, MILD, MODERATE, SEVERE
    }
}