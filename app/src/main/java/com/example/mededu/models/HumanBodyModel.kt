package com.example.mededu.models

data class HumanBodyModel(
    val id: String,
    val modelPath: String,
    val name: String,
    val structures: List<BodyStructure>,
    val annotations: MutableList<Annotation> = mutableListOf()
) {
    fun addAnnotation(annotation: Annotation) {
        annotations.add(annotation)
    }

    fun removeAnnotation(annotationId: String) {
        annotations.removeAll { it.id == annotationId }
    }
}