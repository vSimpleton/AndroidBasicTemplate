package com.vsimpleton.template.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.vsimpleton.template.base.BaseFragment
import com.vsimpleton.template.databinding.FragmentMainBinding
import com.vsimpleton.template.data.viewmodel.MainViewModel

class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val mViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}