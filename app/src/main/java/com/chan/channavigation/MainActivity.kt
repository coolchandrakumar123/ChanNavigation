package com.chan.channavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavGraphBuilder
import androidx.navigation.createGraph
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.get
import com.chan.channavigation.ui.screens.MainFragment
import com.chan.channavigation.ui.navigation.ScreenType
import com.chan.channavigation.ui.navigation.screenList
import com.chan.channavigation.ui.screens.DetailFragment
import com.chan.channavigation.ui.screens.ListFragment
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragment:NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            navHostFragment = NavHostFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, navHostFragment)
                .commitNow()
            prepareNavigation()
        } else {
            navHostFragment = supportFragmentManager
                .findFragmentById(R.id.container) as NavHostFragment
        }
    }

    private fun prepareNavigation() {
        val navController = navHostFragment.navController //findNavController(R.id.nav_host_fragment)
        navController.enableOnBackPressed(true)
        navController.graph = navController.createGraph(
            startDestination = screenList.first().screenName
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

    /*override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }*/
}