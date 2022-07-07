package com.chan.channavigation.ui

import android.content.Context
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.*
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import androidx.navigation.fragment.NavHostFragment
import com.chan.channavigation.ui.navigation.*
import com.chan.channavigation.ui.screens.DetailFragment
import com.chan.channavigation.ui.screens.ListFragment
import com.chan.channavigation.ui.screens.MainFragment
import com.chan.channavigation.ui.screens.NavDrawerFragment
import kotlin.reflect.KClass

/**
 * Created by chandra-1765$ on 05/07/22$.
 */
object UIManager {

    internal fun buildScreens(context: Context, fragmentManager: FragmentManager, @IdRes containerId: Int, startScreen: String?, screenList: ArrayList<Screen>) {
        val navHostFragment = (fragmentManager.findFragmentById(containerId) as? NavHostFragment?)?: kotlin.run {
            NavHostFragment().apply {
                fragmentManager.beginTransaction()
                    .replace(containerId, this)
                    .commitNow()
            }
        }
        prepareNavigation(context, fragmentManager, containerId, navHostFragment, startScreen, screenList)
    }

    private fun prepareNavigation(context: Context, fragmentManager: FragmentManager, @IdRes containerId: Int, navHostFragment: NavHostFragment, startScreen: String?, screenList: ArrayList<Screen>) {
        val navController = navHostFragment.navController //findNavController(R.id.nav_host_fragment)
        navController.enableOnBackPressed(true)
        navController.navigatorProvider += AddFragmentNavigator(context, fragmentManager, containerId)
        navController.graph = navController.createGraph(
            startDestination = startScreen?: screenList.first().screenName
        ) {
            //fragment<MainFragment>("mainFragment")
            screenList.forEach { screen ->
                /*replaceFragment(route = screen.screenName, fragmentClass = getFragment(screenType = screen.screenType)) {
                    label = screen.screenGroup
                }*/
                addFragment(route = screen.screenName, fragmentClass = getFragment(screenType = screen.screenType)) {
                    label = screen.screenGroup
                }
            }
            /*fragment<ListFragment>("listFragment") {
                *//*argument(nav_arguments.plant_id) {
                    type = NavType.StringType
                }*//*
            }*/
        }
    }

    private fun getFragment(screenType: ScreenType) = when (screenType) {
        ScreenType.MAIN -> MainFragment::class
        ScreenType.LIST -> ListFragment::class
        ScreenType.DETAIL -> DetailFragment::class
        ScreenType.NAV_DRAWER -> NavDrawerFragment::class
    }
}