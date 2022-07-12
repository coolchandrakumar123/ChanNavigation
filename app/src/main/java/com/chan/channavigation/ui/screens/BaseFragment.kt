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
import com.chan.channavigation.ui.navigation.Screen
import com.chan.channavigation.ui.navigation.checkAndRemoveGroup
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
        screen = UIScreens.all.find { it.screenName ==  requireView().findNavController().currentDestination?.route}
        Log.d("ChanLog", "onViewCreated: ${screen?.screenName}")
        requireView().findViewById<Button?>(R.id.button)?.setOnClickListener {
            /*requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container, ToolbarFragment.newInstance())
                .addToBackStack("toolBarFragment")
                .commit()*/
            navigateToNextDestination()
        }
        requireView().findViewById<TextView?>(R.id.title)?.text = screen?.screenName?:"Screen"
        if(screen?.applyPassOn == true && !viewModel.passOnApplied) {
            viewModel.passOnApplied = true
            //passOn
            requireView().findNavController().backQueue.lastOrNull()?.let {
                Log.d("ChanLog", "passOn: ${it.destination.route}")
                requireView().findNavController().backQueue.remove(it)
                //requireView().findNavController().navigatorProvider.getNavigator<AddFragmentNavigator>("add_fragment").popBackStack(it)
            }
            navigateToNextDestination()
        }

        if(screen?.removeGroup == true && !viewModel.removeGroupApplied) {
            viewModel.removeGroupApplied = true
            requireView().findNavController().currentDestination?.label?.toString()?.let {
                checkAndRemoveGroup(navController = requireView().findNavController(), screenGroup = it)
            }
        }

        logBackLackRoutes()
    }

    private fun logBackLackRoutes() {
        requireView().findNavController().backQueue.forEach {
            Log.d("ChanLog", "BackQueue, Route: ${it.destination.route} ")
        }
    }

    open fun navigateToNextDestination() {
        /*requireView().navigateTo(
            route = "listFragment"
        )*/
        screen?.navigation?.let {
            requireView().navigateTo(it)
        }
    }
}