package com.yungjohn.mynotes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.yungjohn.mynotes.R
import com.yungjohn.mynotes.databinding.FragmentRecordBinding


class RecordFragment : Fragment() {

    private lateinit var binding: FragmentRecordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.microphoneImage.setOnClickListener {
            this.findNavController().navigate(RecordFragmentDirections.actionRecordFragmentToRecordingFragment())
        }
    }

}