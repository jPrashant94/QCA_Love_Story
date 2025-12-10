package com.unbelievable.justfacts.kotlinmodule.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.unbelievable.justfacts.R
import com.unbelievable.justfacts.kotlinmodule.ADConstant
import com.unbelievable.justfacts.kotlinmodule.Utils.loadStoriesFromAssets
import com.unbelievable.justfacts.kotlinmodule.model.StoryModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var count: Int = 2;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreenUI()
        }


        ADConstant.LoadAdmobInsterstitialAd(this)
    }

    private fun moveToStory(title: String, desc: String) {
        val intent = Intent(this@MainActivity, ReadStoryActivity::class.java)
        intent.putExtra("story_title", title)
        intent.putExtra("story_desc", desc)
        startActivity(intent)
    }


    @Composable
    fun MainScreenUI() {
        var isLoading by remember { mutableStateOf(true) }
        var stories by remember { mutableStateOf(emptyList<StoryModel>()) }

        LaunchedEffect(Unit) {
            lifecycleScope.launch {
                stories = loadStoriesFromAssets(this@MainActivity)
                isLoading = false
            }

        }

        Column(
            modifier = Modifier
                .safeDrawingPadding()
                .fillMaxSize()
                .background(color = Color.Black)
        ) {
            if (isLoading) {
                LoadingScreenUI()
            } else {
                StoryListUI(stories =stories.shuffled()) {
                    story ->
                    count++
                    if(count%3==0){
                        ADConstant.ShowAdmobInsterstitial(
                            this@MainActivity,
                            object : ADConstant.CallBack {
                                override fun MoveToNext() {

                                    moveToStory(story.title,story.des)

                                }

                            })
                    }
                    else {
                        moveToStory(story.title,story.des)

                    }
                }
            }
        }


    }


}



@Composable
fun LoadingScreenUI() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(trackColor = Color.White, color = Color.Green)
        Text(
            text = "Please Wait...",
            color = Color.White,
            modifier = Modifier.padding(16.dp),
            fontSize = 16.sp
        )
    }
}

@Composable
fun StoryListUI(stories: List<StoryModel>, onItemClick: (StoryModel) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(10.dp)) {
        items(stories) { story ->

            Box(
                modifier = Modifier
                    .height(100.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Image(
                    painter = painterResource(R.drawable.bg_1),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = story.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                        .clickable {
                            onItemClick(story)
                        }
                )
            }

        }
    }
}


//@Preview(showBackground = true, widthDp = 480, heightDp = 800, backgroundColor = 1)
@Composable
fun ShowUIPreview() {
    LoadingScreenUI()
}
