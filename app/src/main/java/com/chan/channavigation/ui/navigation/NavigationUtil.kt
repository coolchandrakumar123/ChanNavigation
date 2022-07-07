package com.chan.channavigation.ui.navigation

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.*
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import kotlin.reflect.KClass

/**
 * Created by chandra-1765$ on 06/07/22$.
 */
fun NavGraphBuilder.replaceFragment(
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

fun NavGraphBuilder.addFragment(
    route: String,
    fragmentClass: KClass<out Fragment>,
    builder: AddFragmentNavigatorDestinationBuilder.() -> Unit = {}
): Unit = destination(
    AddFragmentNavigatorDestinationBuilder(
        provider[AddFragmentNavigator::class],
        route,
        fragmentClass
    ).apply(builder)
)

fun View.navigateTo(route: String) {
    findNavController().navigate(
        route = route,
        navOptions = NavOptions.Builder().setLaunchSingleTop(false).setRestoreState(false).build()
    )
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
    //navController.popBackStack(route = "test", inclusive = true)
}

/**
 * DSL for constructing a new [FragmentNavigator.Destination]
 */
@NavDestinationDsl
class AddFragmentNavigatorDestinationBuilder :
    NavDestinationBuilder<AddFragmentNavigator.Destination> {

    private var fragmentClass: KClass<out Fragment>

    /**
     * DSL for constructing a new [FragmentNavigator.Destination]
     *
     * @param navigator navigator used to create the destination
     * @param id the destination's unique id
     * @param fragmentClass The class name of the Fragment to show when you navigate to this
     * destination
     */
    @Suppress("Deprecation")
    @Deprecated(
        "Use routes to build your FragmentNavigatorDestination instead",
        ReplaceWith(
            "FragmentNavigatorDestinationBuilder(navigator, route = id.toString(), fragmentClass) "
        )
    )
    public constructor(
        navigator: AddFragmentNavigator,
        @IdRes id: Int,
        fragmentClass: KClass<out Fragment>
    ) : super(navigator, id) {
        this.fragmentClass = fragmentClass
    }

    /**
     * DSL for constructing a new [AddFragmentNavigator.Destination]
     *
     * @param navigator navigator used to create the destination
     * @param route the destination's unique route
     * @param fragmentClass The class name of the Fragment to show when you navigate to this
     * destination
     */
    public constructor(
        navigator: AddFragmentNavigator,
        route: String,
        fragmentClass: KClass<out Fragment>
    ) : super(navigator, route) {
        this.fragmentClass = fragmentClass
    }

    override fun build(): AddFragmentNavigator.Destination =
        super.build().also { destination ->
            destination.setClassName(fragmentClass.java.name)
        }
}

/**
 * DSL for constructing a new [FragmentNavigator.Destination]
 */
@NavDestinationDsl
class RadarFragmentNavigatorDestinationBuilder :
    NavDestinationBuilder<RadarNavigator.Destination> {

    private var fragmentClass: KClass<out Fragment>

    /**
     * DSL for constructing a new [FragmentNavigator.Destination]
     *
     * @param navigator navigator used to create the destination
     * @param id the destination's unique id
     * @param fragmentClass The class name of the Fragment to show when you navigate to this
     * destination
     */
    @Suppress("Deprecation")
    @Deprecated(
        "Use routes to build your FragmentNavigatorDestination instead",
        ReplaceWith(
            "FragmentNavigatorDestinationBuilder(navigator, route = id.toString(), fragmentClass) "
        )
    )
    public constructor(
        navigator: RadarNavigator,
        @IdRes id: Int,
        fragmentClass: KClass<out Fragment>
    ) : super(navigator, id) {
        this.fragmentClass = fragmentClass
    }

    /**
     * DSL for constructing a new [RadarNavigator.Destination]
     *
     * @param navigator navigator used to create the destination
     * @param route the destination's unique route
     * @param fragmentClass The class name of the Fragment to show when you navigate to this
     * destination
     */
    public constructor(
        navigator: RadarNavigator,
        route: String,
        fragmentClass: KClass<out Fragment>
    ) : super(navigator, route) {
        this.fragmentClass = fragmentClass
    }

    override fun build(): RadarNavigator.Destination =
        super.build().also { destination ->
            destination.setClassName(fragmentClass.java.name)
        }
}