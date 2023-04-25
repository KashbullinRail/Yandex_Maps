package com.example.yandex_maps

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.example.yandex_maps.databinding.ActivityMainBinding
import com.example.yandex_maps.ui.main.BlankFragment
import com.example.yandex_maps.ui.theme.Yandex_MapsTheme
import com.yandex.mapkit.MapKitFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Yandex_MapsTheme {
                AndroidViewBinding(ActivityMainBinding::inflate) {
                    val fragment = container.getFragment<BlankFragment>()
                }
            }
        }
    }
}
