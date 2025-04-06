package com.example.mededu.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mededu.adapters.AnatomyListAdapter
import com.example.mededu.AnnotationDialogFragment
import com.example.mededu.BodyViewerViewModel
import com.example.mededu.views.ModelView

class BodyViewerActivity : AppCompatActivity() {
    private lateinit var modelView: ModelView
    private lateinit var anatomyList: RecyclerView
    private lateinit var viewModel: BodyViewerViewModel
    private lateinit var binding: ActivityBodyViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBodyViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        modelView = binding.modelView
        anatomyList = binding.anatomyList

        setupToolbar()
        setupViewModel()
        setupAnatomyList()
        setupControls()

        loadDefaultModel()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(BodyViewerViewModel::class.java)

        viewModel.currentModel.observe(this) { model ->
            model?.let { modelView.loadModel(it) }
        }

        viewModel.annotations.observe(this) { annotations ->
            modelView.updateAnnotations(annotations)
        }
    }

    private fun setupAnatomyList() {
        anatomyList.layoutManager = LinearLayoutManager(this)
        anatomyList.adapter = AnatomyListAdapter { structure ->
            viewModel.selectStructure(structure)
        }
    }

    private fun setupControls() {
        binding.btnRotate.setOnClickListener { modelView.setInteractionMode(InteractionMode.ROTATE) }
        binding.btnPan.setOnClickListener { modelView.setInteractionMode(InteractionMode.PAN) }
        binding.btnZoom.setOnClickListener { modelView.setInteractionMode(InteractionMode.ZOOM) }
        binding.btnAddNote.setOnClickListener { showAddAnnotationDialog() }
    }

    private fun loadDefaultModel() {
        viewModel.loadModel("human_male")
    }

    private fun showAddAnnotationDialog() {
        val dialog = AnnotationDialogFragment().apply {
            setOnAnnotationCreatedListener { annotation ->
                viewModel.addAnnotation(annotation)
            }
        }
        dialog.show(supportFragmentManager, "AnnotationDialog")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.viewer_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                shareWithDoctor()
                true
            }
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun shareWithDoctor() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, viewModel.createShareText())
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Share with doctor"))
    }
}