package com.example.yandex_maps.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.yandex_maps.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)

        viewModel.reset.observe(viewLifecycleOwner) {
            it?.let {
                binding.counterA.reset()
                binding.counterB.reset()
                viewModel.setReset(null)
            }
        }

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    Button(
                        shape = RoundedCornerShape(8.dp),
                        onClick = {
                            viewModel.setMsg(binding.counterA.result, binding.counterB.result)
                        }) {
                        Text(
                            text = "Compose", modifier = Modifier.padding(
                                horizontal = 60.dp, vertical = 2.dp
                            )
                        )
                    }

                }
            }
        }
        return binding.root
    }

}