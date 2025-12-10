package com.unbelievable.justfacts.kotlinmodule.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.unbelievable.justfacts.GDPR
import com.unbelievable.justfacts.R
import com.unbelievable.justfacts.kotlinmodule.StoryApplication

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SplashScreenUI()
        }

        val gdpr = GDPR(this)
        gdpr.updateGDPRConsentStatus()
        Handler(Looper.getMainLooper()).postDelayed({

            val application = application;
            if (application !is StoryApplication) {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
                return@postDelayed
            }

            (application as StoryApplication).showAdIfAvailable(
                this@SplashActivity,
                object : StoryApplication.OnShowAdCompleteListener {
                    override fun onShowAdComplete() {
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                })

        }, 8000)
    }
}

@Composable
fun SplashScreenUI()
{
    Column(
      modifier = Modifier.safeDrawingPadding().fillMaxSize().background(MaterialTheme.colorScheme.primary),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.round_icon),
                contentDescription = null,
                modifier = Modifier.width(100.dp).padding(16.dp)
            )
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )

        }
       Column(modifier = Modifier.fillMaxWidth().height(100.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
           LinearProgressIndicator(modifier = Modifier.fillMaxWidth(0.8f), trackColor = Color.White, color = Color.Green, )
       }

    }
}

@Preview(showBackground = true)
@Composable
fun ShowPreview()
{
    SplashScreenUI()
}
