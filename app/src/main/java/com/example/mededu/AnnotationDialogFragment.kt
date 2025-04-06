package com.example.mededu

class AnnotationDialogFragment : DialogFragment() {
    private var listener: OnAnnotationCreatedListener? = null

    interface OnAnnotationCreatedListener {
        fun onAnnotationCreated(annotation: Annotation)
    }

    fun setOnAnnotationCreatedListener(listener: OnAnnotationCreatedListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_annotation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noteEditText = view.findViewById<EditText>(R.id.editNote)
        val painLevelSpinner = view.findViewById<Spinner>(R.id.spinnerPainLevel)
        val btnSubmit = view.findViewById<Button>(R.id.btnSubmit)

        // Setup pain level spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.pain_levels,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            painLevelSpinner.adapter = adapter
        }

        btnSubmit.setOnClickListener {
            val note = noteEditText.text.toString()
            if (note.isNotBlank()) {
                val painLevel = when (painLevelSpinner.selectedItemPosition) {
                    1 -> Annotation.PainLevel.MILD
                    2 -> Annotation.PainLevel.MODERATE
                    3 -> Annotation.PainLevel.SEVERE
                    else -> Annotation.PainLevel.NONE
                }

                // In a real app, you'd get the position from the 3D view
                val position = Vector3(0f, 0f, 0f)

                listener?.onAnnotationCreated(
                    Annotation(
                        note = note,
                        painLevel = painLevel,
                        position = position
                    )
                )
                dismiss()
            } else {
                noteEditText.error = "Please enter a note"
            }
        }
    }
}