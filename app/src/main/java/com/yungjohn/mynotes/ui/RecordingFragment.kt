package com.yungjohn.mynotes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yungjohn.mynotes.R
import com.yungjohn.mynotes.databinding.FragmentRecordingBinding
import com.yungjohn.mynotes.viewmodel.RecordingViewModel

class RecordingFragment : Fragment() {

    private lateinit var binding: FragmentRecordingBinding
    private lateinit var viewModel: RecordingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecordingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}