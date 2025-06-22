package com.ucne.yohualkis_tejada_ap2_p2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ucne.yohualkis_tejada_ap2_p2.presentation.navigation.GeneralNavHost
import com.ucne.yohualkis_tejada_ap2_p2.ui.theme.Yohualkis_Tejada_AP2_P2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Yohualkis_Tejada_AP2_P2Theme {
                val nav = rememberNavController()
                Scaffold { innerPadding ->
                    GeneralNavHost(
                        nav,
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}