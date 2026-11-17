package com.sigap.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sigap.mobile.navigation.AppNavGraph
import com.sigap.mobile.ui.theme.SIGAPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SIGAPTheme {
                AppNavGraph()
            }
        }
    }
}