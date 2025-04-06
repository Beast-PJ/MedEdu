package com.example.mededu

class BodyViewerViewModel(application: Application) : AndroidViewModel(application) {
    private val _currentModel = MutableLiveData<HumanBodyModel>()
    val currentModel: LiveData<HumanBodyModel> = _currentModel

    private val _selectedStructure = MutableLiveData<BodyStructure?>()
    val selectedStructure: LiveData<BodyStructure?> = _selectedStructure

    private val _annotations = MutableLiveData<List<Annotation>>()
    val annotations: LiveData<List<Annotation>> = _annotations

    private val modelRepository: ModelRepository = ModelRepository(application)

    fun loadModel(modelId: String) {
        viewModelScope.launch {
            val model = modelRepository.loadModel(modelId)
            _currentModel.postValue(model)
            _annotations.postValue(model.annotations)
        }
    }

    fun selectStructure(structure: BodyStructure) {
        _selectedStructure.postValue(structure)
    }

    fun addAnnotation(annotation: Annotation) {
        val current = _currentModel.value ?: return
        current.addAnnotation(annotation)
        _currentModel.postValue(current)
        _annotations.postValue(current.annotations)
    }

    fun createShareText(): String {
        val model = _currentModel.value ?: return ""
        val sb = StringBuilder()

        sb.append("Medical 3D Viewer Report\n\n")
        sb.append("Model: ${model.name}\n\n")
        sb.append("Annotations:\n")

        model.annotations.forEach { annotation ->
            sb.append("- ${annotation.note} (Pain: ${annotation.painLevel})\n")
        }

        return sb.toString()
    }
}