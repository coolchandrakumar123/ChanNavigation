package com.chan.channavigation.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.chan.channavigation.R
import com.chan.channavigation.ui.UIManager
import com.chan.channavigation.ui.navigation.Screen
import com.chan.channavigation.ui.navigation.navigateTo

/**
 * Created by chandra-1765$ on 01/07/22$.
 */
abstract class BaseFragment: Fragment() {

    internal lateinit var viewModel: MainViewModel
    var screen: Screen? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        screen = UIManager.allScreenList.find { it.screenName ==  requireView().findNavController().currentDestination?.route}
        Log.d("ChanLog", "onViewCreated: ${screen?.screenName}")
        requireView().findViewById<Button?>(R.id.button)?.setOnClickListener {
            /*requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container, ToolbarFragment.newInstance())
                .addToBackStack("toolBarFragment")
                .commit()*/
            navigateToNextDestination()
        }
        requireView().findViewById<TextView?>(R.id.title)?.text = screen?.screenName?:"Screen"
        if(screen?.applyPassOn == true) {
            //passOn
            navigateToNextDestination()
        }

        if(screen?.viaPassOn == true) {
            //passOn
            requireView().findNavController().backQueue.removeLastOrNull()
        }
    }

    fun navigateToNextDestination() {
        /*requireView().navigateTo(
            route = "listFragment"
        )*/
        screen?.navigation?.let {
            requireView().navigateTo(it)
        }
    }
}