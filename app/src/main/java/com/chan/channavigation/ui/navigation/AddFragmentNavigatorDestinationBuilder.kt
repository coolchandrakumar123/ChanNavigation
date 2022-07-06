package com.chan.channavigation.ui.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDestinationBuilder
import androidx.navigation.NavDestinationDsl
import androidx.navigation.fragment.FragmentNavigator
import kotlin.reflect.KClass

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