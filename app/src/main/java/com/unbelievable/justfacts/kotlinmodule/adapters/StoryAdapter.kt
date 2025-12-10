package com.unbelievable.justfacts.kotlinmodule.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unbelievable.justfacts.R
import com.unbelievable.justfacts.kotlinmodule.model.StoryModel

class StoryAdapter(
    private val stories: List<StoryModel>,
    private val onItemClick: (StoryModel) -> Unit
) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.subTitle)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoryAdapter.StoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.story_list_adapter, parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryAdapter.StoryViewHolder, position: Int) {
        val story = stories[position]
        holder.title.text = story.title

        holder.itemView.setOnClickListener { onItemClick(story) }
    }

    override fun getItemCount(): Int {
        return stories.size
    }

}