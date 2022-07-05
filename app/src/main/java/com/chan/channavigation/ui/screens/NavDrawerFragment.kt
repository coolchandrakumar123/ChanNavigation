package com.chan.channavigation.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.chan.channavigation.R
import com.chan.channavigation.ui.UIManager
import com.google.android.material.navigation.NavigationView


class NavDrawerFragment : BaseFragment() {

    init {
        Log.d("ChanLog", "NavDrawerFragment: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.nav_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UIManager.buildScreens(childFragmentManager, R.id.container, "KBCategory")
    }

}