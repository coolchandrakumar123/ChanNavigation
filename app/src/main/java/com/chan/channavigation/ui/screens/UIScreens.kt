package com.chan.channavigation.ui.screens

import com.chan.channavigation.ui.navigation.Screen
import com.chan.channavigation.ui.navigation.ScreenType

/**
 * Created by chandra-1765$ on 06/07/22$.
 */
object UIScreens {
    val all = arrayListOf(
        Screen(
            screenId = 101,
            screenType = ScreenType.MAIN,
            screenName = "KBCategory",
            screenGroup = "KB",
            navigation = "KBSubCategory",
            applyPassOn = true
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

    val navDrawer = arrayListOf(
        Screen(
            screenId = 1011,
            screenType = ScreenType.NAV_DRAWER,
            screenName = "KBNavDrawer",
            screenGroup = "KB",
            navigation = "KBCategory"
        )
    )
}