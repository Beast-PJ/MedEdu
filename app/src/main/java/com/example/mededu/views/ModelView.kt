package com.example.mededu.views

import com.example.mededu.models.HumanBodyModel

class ModelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {
    private var engine: Engine
    private var renderer: Renderer
    private var scene: Scene
    private var view: View
    private var camera: Camera
    private var swapChain: SwapChain? = null
    private var modelViewer: ModelViewer

    private var lastX: Float = 0f
    private var lastY: Float = 0f
    private var interactionMode: InteractionMode = InteractionMode.ROTATE

    init {
        holder.addCallback(this)

        engine = Engine.create()
        renderer = engine.createRenderer()
        scene = engine.createScene()
        camera = engine.createCamera()
        view = engine.createView()

        modelViewer = ModelViewer(
            engine = engine,
            renderer = renderer,
            scene = scene,
            view = view,
            camera = camera
        )

        setupLighting()
        setupGestureDetector()
    }

    private fun setupLighting() {
        val sunlight = EntityManager.get().create()
        LightManager.Builder(LightManager.Type.DIRECTIONAL)
            .color(1.0f, 1.0f, 0.95f)
            .intensity(100000.0f)
            .direction(0.5f, -1.0f, -0.5f)
            .build(engine, sunlight)
        scene.addEntity(sunlight)
    }

    fun loadModel(model: HumanBodyModel) {
        // Load model from assets
        val assetManager = context.assets
        val buffer = assetManager.open(model.modelPath).use { input ->
            ByteBuffer.allocateDirect(input.available()).apply {
                val bytes = ByteArray(input.available())
                input.read(bytes)
                put(bytes)
                rewind()
            }
        }

        modelViewer.loadModelGlb(buffer)
        modelViewer.transformToUnitCube()
    }

    fun updateAnnotations(annotations: List<Annotation>) {
        // Clear existing annotations
        modelViewer.clearAnnotations()

        // Add new annotations
        annotations.forEach { annotation ->
            modelViewer.addAnnotation(
                position = annotation.position,
                text = annotation.note,
                color = when (annotation.painLevel) {
                    PainLevel.MILD -> Color.rgb(255, 255, 0)
                    PainLevel.MODERATE -> Color.rgb(255, 165, 0)
                    PainLevel.SEVERE -> Color.rgb(255, 0, 0)
                    else -> Color.rgb(0, 255, 0)
                }
            )
        }
    }

    fun setInteractionMode(mode: InteractionMode) {
        interactionMode = mode
    }

    private fun setupGestureDetector() {
        val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean = true

            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                when (interactionMode) {
                    InteractionMode.ROTATE -> {
                        modelViewer.rotate(distanceX * 0.5f, distanceY * 0.5f)
                    }
                    InteractionMode.PAN -> {
                        modelViewer.pan(distanceX * 0.01f, distanceY * 0.01f)
                    }
                    InteractionMode.ZOOM -> {
                        modelViewer.zoom(distanceY * 0.01f)
                    }
                }
                return true
            }
        })

        setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        swapChain = engine.createSwapChain(holder.surface)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        view.viewport = Viewport(0, 0, width, height)
        camera.setProjection(45.0, width.toDouble() / height, 0.1, 1000.0)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        engine.destroySwapChain(swapChain)
        swapChain = null
    }

    fun render() {
        if (swapChain != null) {
            renderer.render(swapChain!!)
        }
    }

    enum class InteractionMode {
        ROTATE, PAN, ZOOM
    }
}
