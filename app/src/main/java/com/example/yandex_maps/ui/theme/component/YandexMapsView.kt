package com.example.yandex_maps.ui.theme.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.yandex_maps.databinding.ComponentMapsBinding

class YandexMapsView(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    var binding: ComponentMapsBinding

    init {
        binding = ComponentMapsBinding.inflate(
            LayoutInflater.from(context), this, true
        )
    }

}