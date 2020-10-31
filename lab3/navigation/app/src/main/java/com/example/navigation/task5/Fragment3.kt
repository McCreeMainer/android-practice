package com.example.navigation.task5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.navigation.R
import com.example.navigation.databinding.Task5Fragment3Binding

class Fragment3 : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = Task5Fragment3Binding.inflate(layoutInflater)

        binding.button1.setOnClickListener {
            view?.findNavController()?.navigate(R.id.third_to_first)
        }

        binding.button2.setOnClickListener {
            view?.findNavController()?.navigate(R.id.third_to_second)
        }

        return binding.root
    }
}