package com.example.mededu.models

data class BodyStructure(
    val id: String,
    val name: String,
    val latinName: String? = null,
    val description: String,
    val partOf: List<String> = emptyList(),
    val subStructures: List<String> = emptyList(),
    val meshIds: List<String>,
    val systems: List<BodySystem>
) {
    enum class BodySystem {
        SKELETAL, MUSCULAR, NERVOUS, CIRCULATORY,
        RESPIRATORY, DIGESTIVE, URINARY,
        REPRODUCTIVE, ENDOCRINE, LYMPHATIC, INTEGUMENTARY
    }
}