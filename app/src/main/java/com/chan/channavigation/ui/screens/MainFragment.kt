package com.chan.channavigation.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.chan.channavigation.R


class MainFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireView().findNavController().backQueue.forEach {
            Log.d("ChanLog", "MainFragment: ${it.id}, Route: ${it.destination.route} ")
        }
        /*requireView().findNavController().currentDestination?.route?.let {
            checkAndRemoveGroup(screenGroup = it)
        }*/
    }

    /**
     * Select And Remove Groups
     */
    private fun checkAndRemoveGroup(screenGroup: String) {
        requireView().findNavController().backQueue.apply {
            find { it.destination.route == screenGroup }?.destination?.label?.let { currentScreenGroup ->
                for (index in (lastIndex - 1) downTo 0) {
                    val navBackStackEntry = this[index]
                    navBackStackEntry.updateState()
                    if(currentScreenGroup == navBackStackEntry.destination.label) {
                        this.remove(navBackStackEntry)
                    }
                }
            }
        }
    }

}