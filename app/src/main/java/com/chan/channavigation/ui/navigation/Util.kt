package com.chan.channavigation.ui.navigation

import android.view.View
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

data class Screen(val screenId: Int, val screenType: ScreenType, val screenName: String, val screenGroup: String, val navigation: String)

enum class ScreenType{
    MAIN, LIST, DETAIL, NAV_DRAWER
}