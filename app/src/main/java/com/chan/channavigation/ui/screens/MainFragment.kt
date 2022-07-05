package com.chan.channavigation.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.chan.channavigation.R
import com.chan.channavigation.ui.navigation.checkAndRemoveGroup


class MainFragment : BaseFragment() {

    init {
        Log.d("ChanLog", "MainFragment: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*requireView().findNavController().backQueue.forEach {
            Log.d("ChanLog", "MainFragment - Route: ${it.destination.route} ")
        }*/
        /*requireView().findNavController().currentDestination?.route?.let {
            checkAndRemoveGroup(navController = requireView().findNavController(), screenGroup = it)
        }*/
    }

}