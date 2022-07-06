package com.chan.channavigation.ui.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.NavGraphBuilder
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import androidx.navigation.get
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