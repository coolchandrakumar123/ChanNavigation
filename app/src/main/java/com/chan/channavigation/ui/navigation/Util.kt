package com.chan.channavigation.ui.navigation

/**
 * Created by chandra-1765$ on 01/07/22$.
 */

data class Screen(val screenId: Int, val screenType: ScreenType, val screenName: String, val screenGroup: String, val navigation: String, var applyPassOn: Boolean = false, var removeGroup: Boolean = false)

enum class ScreenType{
    MAIN, LIST, DETAIL, NAV_DRAWER
}