package com.chan.channavigation.ui.navigation

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController

/**
 * Created by chandra-1765$ on 01/07/22$.
 */

fun View.navigateTo(route: String) {
    findNavController().navigate(
        route = route,
        navOptions = NavOptions.Builder().setLaunchSingleTop(true).setRestoreState(true).build()
    )
}

data class Screen(val screenId: Int, val screenType: ScreenType, val screenName: String, val screenGroup: String, val navigation: String, var applyPassOn: Boolean = false, var viaPassOn: Boolean = false)

enum class ScreenType{
    MAIN, LIST, DETAIL, NAV_DRAWER
}

/**
 * Select And Remove Groups
 */
fun checkAndRemoveGroup(navController: NavController, screenGroup: String) {
    navController.backQueue.apply {
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