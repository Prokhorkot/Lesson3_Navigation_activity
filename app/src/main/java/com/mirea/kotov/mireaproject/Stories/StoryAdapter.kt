package com.mirea.kotov.mireaproject.Stories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirea.kotov.mireaproject.R
import com.mirea.kotov.mireaproject.databinding.StoryItemBinding

class StoryAdapter: RecyclerView.Adapter<StoryAdapter.StoryHolder>(){
    private val storyArray = ArrayList<Story>()

    class StoryHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = StoryItemBinding.bind(view)

        fun bind(story: Story)= with(binding){
            storyImage.setImageURI(story.imageUri)
            tvName.text = story.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.story_item, parent, false)

        return StoryHolder(view)
    }

    override fun onBindViewHolder(holder: StoryHolder, position: Int) {
        holder.bind(storyArray[position])
    }

    override fun getItemCount(): Int {
        return storyArray.size
    }

    fun addStory(story: Story){
        storyArray.add(story)
        notifyDataSetChanged()
    }
}