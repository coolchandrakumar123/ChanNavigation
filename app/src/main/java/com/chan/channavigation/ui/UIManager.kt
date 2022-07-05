package com.chan.channavigation.ui

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavGraphBuilder
import androidx.navigation.createGraph
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.get
import com.chan.channavigation.ui.navigation.Screen
import com.chan.channavigation.ui.navigation.ScreenType
import com.chan.channavigation.ui.screens.DetailFragment
import com.chan.channavigation.ui.screens.ListFragment
import com.chan.channavigation.ui.screens.MainFragment
import com.chan.channavigation.ui.screens.NavDrawerFragment
import kotlin.reflect.KClass

/**
 * Created by chandra-1765$ on 05/07/22$.
 */
object UIManager {

    val allScreenList = arrayListOf(
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

    internal fun buildScreens(fragmentManager: FragmentManager, @IdRes containerId: Int, startScreen: String?, screenList: ArrayList<Screen>) {
        (fragmentManager.findFragmentById(containerId) as? NavHostFragment?)?.let {
            prepareNavigation(it, startScreen, screenList)
        }?: kotlin.run {
            val navHostFragment = NavHostFragment()
            fragmentManager.beginTransaction()
                .replace(containerId, navHostFragment)
                .commitNow()
            prepareNavigation(navHostFragment, startScreen, screenList)
        }
    }

    private fun prepareNavigation(navHostFragment: NavHostFragment, startScreen: String?, screenList: ArrayList<Screen>) {
        val navController = navHostFragment.navController //findNavController(R.id.nav_host_fragment)
        navController.enableOnBackPressed(true)
        navController.graph = navController.createGraph(
            startDestination = startScreen?: screenList.first().screenName
        ) {
            //fragment<MainFragment>("mainFragment")
            screenList.forEach { screen ->
                customFragment(route = screen.screenName, fragmentClass = getFragment(screenType = screen.screenType)) {
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

    private fun NavGraphBuilder.customFragment(
        route: String,
        fragmentClass: KClass<out Fragment>,
        builder: FragmentNavigatorDestinationBuilder.() -> Unit = {}
    ): Unit = destination(
        FragmentNavigatorDestinationBuilder(
            provider[FragmentNavigator::class],
            route,
            fragmentClass
        ).apply(builder)
    )
}