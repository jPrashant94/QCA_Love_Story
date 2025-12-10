package com.unbelievable.justfacts.kotlinmodule

import android.content.Context
import com.unbelievable.justfacts.kotlinmodule.model.StoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

object Utils {

    suspend fun loadStoriesFromAssets(context: Context): List<StoryModel> {
        return withContext(Dispatchers.IO) {
            try {
                val json = context.assets.open("stories.json")
                    .bufferedReader()
                    .use { it.readText() }

                val root = JSONObject(json)
                val storiesJson: JSONArray = root.getJSONArray("Stories")

                println("Extracted JSON Array: $storiesJson")

                val stories = mutableListOf<StoryModel>()

                for (i in 0 until storiesJson.length()) {
                    val item = storiesJson.getJSONObject(i)
                    val story = StoryModel(
                        id = item.optInt("id", 0),
                        title = item.optString("title", ""),
                        des = item.optString("des", "")
                    )
                    stories.add(story)
                }

                stories
            } catch (e: Exception) {
                println("* ERROR ERROR ERROR * ${e.message}")
                e.printStackTrace()
                emptyList()
            }
        }
    }
}