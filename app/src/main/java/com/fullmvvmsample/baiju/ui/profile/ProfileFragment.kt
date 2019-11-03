package com.fullmvvmsample.baiju.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.fullmvvmsample.baiju.R
import com.fullmvvmsample.baiju.databinding.ProfileFragmentBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProfileFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private lateinit var viewModel: ProfileViewModel
    private val factory: ProfileViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: ProfileFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)

        viewModel = ViewModelProviders.of(this, factory).get(ProfileViewModel::class.java)
        binding.profileViewmodel = viewModel

        // mandatory to use "lifecycleOwner" when u are binding LiveData to layout
        binding.lifecycleOwner = this

        return binding.root
    }


}