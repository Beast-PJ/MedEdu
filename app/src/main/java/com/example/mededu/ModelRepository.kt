package com.example.mededu

class ModelRepository(private val context: Context) {
    private val gson = Gson()

    suspend fun loadModel(modelId: String): HumanBodyModel {
        return withContext(Dispatchers.IO) {
            // In a real app, this would load from database or network
            // Here we're loading a sample model from assets

            val modelJson = context.assets.open("models/$modelId.json")
                .bufferedReader().use { it.readText() }

            val structuresJson = context.assets.open("models/structures.json")
                .bufferedReader().use { it.readText() }

            val structures = gson.fromJson(structuresJson, Array<BodyStructure>::class.java).toList()

            gson.fromJson(modelJson, HumanBodyModel::class.java).copy(
                structures = structures
            )
        }
    }
}