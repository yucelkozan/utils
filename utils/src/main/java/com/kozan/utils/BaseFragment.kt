package com.kozan.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels



abstract class BaseFragment<DB : ViewDataBinding>(@LayoutRes private val layoutResId : Int) : Fragment() {

    lateinit var binding: DB
    //val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<DB>(inflater, layoutResId, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

}