package com.example.mededu.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mededu.R
import com.example.mededu.models.BodyStructure

class AnatomyListAdapter(
    private val onItemClick: (BodyStructure) -> Unit
) : RecyclerView.Adapter<AnatomyListAdapter.ViewHolder>() {

    private var structures: List<BodyStructure> = emptyList()

    fun updateStructures(newStructures: List<BodyStructure>) {
        structures = newStructures
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_anatomy, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(structures[position])
    }

    override fun getItemCount() = structures.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.structureName)
        private val systemTextView: TextView = itemView.findViewById(R.id.structureSystem)

        fun bind(structure: BodyStructure) {
            nameTextView.text = structure.name
            systemTextView.text = structure.systems.joinToString { it.name }

            itemView.setOnClickListener {
                onItemClick(structure)
            }
        }
    }
}