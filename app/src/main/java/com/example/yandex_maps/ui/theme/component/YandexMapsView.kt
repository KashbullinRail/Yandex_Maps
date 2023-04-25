package com.example.yandex_maps.ui.theme.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.yandex_maps.databinding.ComponentCounterBinding

class YandexMapsView(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    var binding: ComponentCounterBinding

    init {
        binding = ComponentCounterBinding.inflate(LayoutInflater.from(context), this, true)
    }

}