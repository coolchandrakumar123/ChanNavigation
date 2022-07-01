package com.chan.channavigation.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.chan.channavigation.R
import com.chan.channavigation.ui.navigation.navigateTo


class ListFragment : BaseFragment() {

    init {
        Log.d("ChanLog", "ListFragment: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //passOn
        //navigateToNextDestination()
    }

    /*private fun navigateToPlant() {
        requireView().navigateTo(route = "detailFragment")
    }*/

}