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

val screenList = arrayListOf(
    Screen(
        screenId = 101,
        screenType = ScreenType.MAIN,
        screenName = "KBCategory",
        screenGroup = "KB",
        navigation = "KBSubCategory"
    ),
    Screen(
        screenId = 102,
        screenType = ScreenType.LIST,
        screenName = "KBSubCategory",
        screenGroup = "KB",
        navigation = "KBDetail"
    ),
    Screen(
        screenId = 103,
        screenType = ScreenType.DETAIL,
        screenName = "KBDetail",
        screenGroup = "KB",
        navigation = "CommunityCategory"
    ),
    Screen(
        screenId = 104,
        screenType = ScreenType.MAIN,
        screenName = "CommunityCategory",
        screenGroup = "Community",
        navigation = "CommunitySubCategory"
    ),
    Screen(
        screenId = 105,
        screenType = ScreenType.LIST,
        screenName = "CommunitySubCategory",
        screenGroup = "Community",
        navigation = "CommunityDetail"
    ),
    Screen(
        screenId = 106,
        screenType = ScreenType.DETAIL,
        screenName = "CommunityDetail",
        screenGroup = "Community",
        navigation = "KBCategory"
    ),
)

data class Screen(val screenId: Int, val screenType: ScreenType, val screenName: String, val screenGroup: String, val navigation: String)

enum class ScreenType{
    MAIN, LIST, DETAIL
}