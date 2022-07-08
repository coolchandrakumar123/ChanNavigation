package com.chan.channavigation.ui.navigation

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.*
import java.util.*


@Navigator.Name("radar_add_fragment")
class AddOldNavigator(
    private val mContext: Context, private val mFragmentManager: FragmentManager,
    private val mContainerId: Int
) : Navigator<AddOldNavigator.Destination>() {
    private val mBackStack = ArrayDeque<Int>()

    /**
     * {@inheritDoc}
     *
     *
     * This method must call
     * [FragmentTransaction.setPrimaryNavigationFragment]
     * if the pop succeeded so that the newly visible Fragment can be retrieved with
     * [FragmentManager.getPrimaryNavigationFragment].
     *
     *
     * Note that the default implementation pops the Fragment
     * asynchronously, so the newly visible Fragment from the back stack
     * is not instantly available after this call completes.
     */
    override fun popBackStack(): Boolean {
        if (mBackStack.isEmpty()) {
            return false
        }
        if (mFragmentManager.isStateSaved) {
            Log.i(
                TAG,
                "Ignoring popBackStack() call: FragmentManager has already" + " saved its state"
            )
            return false
        }
        mFragmentManager.popBackStack(
            generateBackStackName(mBackStack.size, mBackStack.peekLast()),
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        mBackStack.removeLast()
        return true
    }

    override fun createDestination(): Destination {
        return Destination(this)
    }

    /**
     * Instantiates the Fragment via the FragmentManager's
     * [androidx.fragment.app.FragmentFactory].
     *
     * Note that this method is **not** responsible for calling
     * [Fragment.setArguments] on the returned Fragment instance.
     *
     * @param context Context providing the correct [ClassLoader]
     * @param fragmentManager FragmentManager the Fragment will be added to
     * @param className The Fragment to instantiate
     * @param args The Fragment's arguments, if any
     * @return A new fragment instance.
     */
    @Deprecated(
        "Set a custom {@link androidx.fragment.app.FragmentFactory} via\n" +
                "      {@link FragmentManager#setFragmentFactory(FragmentFactory)} to control\n" +
                "      instantiation of Fragments."
    )
    // needed to maintain forward compatibility
    fun instantiateFragment(
        context: Context,
        fragmentManager: FragmentManager,
        className: String, args: Bundle?
    ): Fragment {
        return fragmentManager.fragmentFactory.instantiate(
            context.classLoader, className
        )
    }

    /**
     * {@inheritDoc}
     *
     *
     * This method should always call
     * [FragmentTransaction.setPrimaryNavigationFragment]
     * so that the Fragment associated with the new destination can be retrieved with
     * [FragmentManager.getPrimaryNavigationFragment].
     *
     *
     * Note that the default implementation commits the new Fragment
     * asynchronously, so the new Fragment is not instantly available
     * after this call completes.
     */
    override/* Using instantiateFragment for forward compatibility */ fun navigate(
        destination: Destination, args: Bundle?,
        navOptions: NavOptions?, navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        if (mFragmentManager.isStateSaved) {
            Log.i(TAG, "Ignoring navigate() call: FragmentManager has already" + " saved its state")
            return null
        }
        var className = destination.className
        if (className!![0] == '.') {
            className = mContext.packageName + className
        }
        val frag = instantiateFragment(
            mContext, mFragmentManager,
            className, args
        )
        frag.arguments = args
        val ft = mFragmentManager.beginTransaction()

        var enterAnim = if (navOptions != null) navOptions!!.enterAnim else -1
        var exitAnim = if (navOptions != null) navOptions!!.exitAnim else -1
        var popEnterAnim = if (navOptions != null) navOptions!!.popEnterAnim else -1
        var popExitAnim = if (navOptions != null) navOptions!!.popExitAnim else -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

        ft.add(mContainerId, frag)
        ft.addToBackStack(generateBackStackName(mBackStack.size, destination.id))

        ft.setPrimaryNavigationFragment(frag)

        @IdRes val destId = destination.id
        val initialNavigation = mBackStack.isEmpty()
        // TODO Build first class singleTop behavior for fragments
        val isSingleTopReplacement = (navOptions != null && !initialNavigation
                && navOptions!!.shouldLaunchSingleTop()
                && mBackStack.peekLast() == destId)

        val isAdded: Boolean
        if (initialNavigation) {
            isAdded = true
        } else if (isSingleTopReplacement) {
            // Single Top means we only want one instance on the back stack
            if (mBackStack.size > 1) {
                // If the Fragment to be replaced is on the FragmentManager's
                // back stack, a simple replace() isn't enough so we
                // remove it from the back stack and put our replacement
                // on the back stack in its place
                mFragmentManager.popBackStack(
                    generateBackStackName(mBackStack.size, mBackStack.peekLast()),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                ft.addToBackStack(generateBackStackName(mBackStack.size, destId))
            }
            isAdded = false
        } else {
            ft.addToBackStack(generateBackStackName(mBackStack.size + 1, destId))
            isAdded = true
        }
        if (navigatorExtras is Extras) {
            val extras = navigatorExtras as Extras?
            for (sharedElement in extras!!.sharedElements.entries) {
                ft.addSharedElement(sharedElement.key, sharedElement.value)
            }
        }
        ft.setReorderingAllowed(true)
        ft.commit()
        // The commit succeeded, update our view of the world
        if (isAdded) {
            mBackStack.add(destId)
            return destination
        } else {
            return null
        }
    }

    override fun onSaveState(): Bundle? {
        val b = Bundle()
        val backStack = IntArray(mBackStack.size)
        var index = 0
        for (id in mBackStack) {
            backStack[index++] = id!!
        }
        b.putIntArray(KEY_BACK_STACK_IDS, backStack)
        return b
    }

    override fun onRestoreState(savedState: Bundle) {
        if (savedState != null) {
            val backStack = savedState!!.getIntArray(KEY_BACK_STACK_IDS)
            if (backStack != null) {
                mBackStack.clear()
                for (destId in backStack!!) {
                    mBackStack.add(destId)
                }
            }
        }
    }

    private fun generateBackStackName(backStackIndex: Int, destId: Int): String {
//        return (backStackIndex).toString() + "-" + destId
        return destId.toString()
    }

    private fun getDestId(backStackName: String?): Int {
        val split =
            if (backStackName != null) backStackName!!.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray() else arrayOfNulls<String>(
                0
            )
        if (split.size != 2) {
            throw IllegalStateException(
                "Invalid back stack entry on the "
                        + "NavHostFragment's back stack - use getChildFragmentManager() "
                        + "if you need to do custom FragmentTransactions from within "
                        + "Fragments created via your navigation graph."
            )
        }
        try {
            // Just make sure the backStackIndex is correctly formatted
            Integer.parseInt(split[0].orEmpty())
            return Integer.parseInt(split[1].orEmpty())
        } catch (e: NumberFormatException) {
            throw IllegalStateException(
                ("Invalid back stack entry on the "
                        + "NavHostFragment's back stack - use getChildFragmentManager() "
                        + "if you need to do custom FragmentTransactions from within "
                        + "Fragments created via your navigation graph.")
            )
        }

    }

    /**
     * NavDestination specific to [FragmentNavigator]
     */
    @NavDestination.ClassType(Fragment::class)
    class Destination
    /**
     * Construct a new fragment destination. This destination is not valid until you set the
     * Fragment via [.setClassName].
     *
     * @param fragmentNavigator The [FragmentNavigator] which this destination
     * will be associated with. Generally retrieved via a
     * [NavController]'s
     * [NavigatorProvider.getNavigator] method.
     */
        (fragmentNavigator: Navigator<out Destination>) : NavDestination(fragmentNavigator) {

        private var mClassName: String? = null

        /**
         * Gets the Fragment's class name associated with this destination
         *
         * @throws IllegalStateException when no Fragment class was set.
         */
        val className: String?
            get() {
                if (mClassName == null) {
                    throw IllegalStateException("Fragment class was not set")
                }
                return mClassName
            }

        /**
         * Construct a new fragment destination. This destination is not valid until you set the
         * Fragment via [.setClassName].
         *
         * @param navigatorProvider The [NavController] which this destination
         * will be associated with.
         */
        constructor(navigatorProvider: NavigatorProvider) : this(
            navigatorProvider.getNavigator<AddOldNavigator>(
                AddOldNavigator::class.java!!
            )
        ) {
        }

        @CallSuper
        override fun onInflate(context: Context, attrs: AttributeSet) {
            super.onInflate(context, attrs)
            val a = context.resources.obtainAttributes(
                attrs,
                androidx.navigation.fragment.R.styleable.FragmentNavigator
            )
            val className =
                a.getString(androidx.navigation.fragment.R.styleable.FragmentNavigator_android_name)
            if (className != null) {
                setClassName(className!!)
            }
            a.recycle()
        }

        /**
         * Set the Fragment class name associated with this destination
         * @param className The class name of the Fragment to show when you navigate to this
         * destination
         * @return this [Destination]
         */
        fun setClassName(className: String): Destination {
            mClassName = className
            return this
        }
    }

    /**
     * Extras that can be passed to FragmentNavigator to enable Fragment specific behavior
     */
    class Extras internal constructor(sharedElements: Map<View, String>) : Navigator.Extras {
        private val mSharedElements = java.util.LinkedHashMap<View, String>()

        /**
         * Gets the map of shared elements associated with these Extras. The returned map
         * is an [unmodifiable][Collections.unmodifiableMap] copy of the underlying
         * map and should be treated as immutable.
         */
        val sharedElements: Map<View, String>
            get() = Collections.unmodifiableMap(mSharedElements)

        init {
            mSharedElements.putAll(sharedElements)
        }

        /**
         * Builder for constructing new [Extras] instances. The resulting instances are
         * immutable.
         */
        class Builder {
            private val mSharedElements = java.util.LinkedHashMap<View, String>()

            /**
             * Adds multiple shared elements for mapping Views in the current Fragment to
             * transitionNames in the Fragment being navigated to.
             *
             * @param sharedElements Shared element pairs to add
             * @return this [Builder]
             */
            fun addSharedElements(sharedElements: Map<View, String>): Builder {
                for (sharedElement in sharedElements.entries) {
                    val view = sharedElement.key
                    val name = sharedElement.value
                    if (view != null && name != null) {
                        addSharedElement(view!!, name!!)
                    }
                }
                return this
            }

            /**
             * Maps the given View in the current Fragment to the given transition name in the
             * Fragment being navigated to.
             *
             * @param sharedElement A View in the current Fragment to match with a View in the
             * Fragment being navigated to.
             * @param name The transitionName of the View in the Fragment being navigated to that
             * should be matched to the shared element.
             * @return this [Builder]
             * @see FragmentTransaction.addSharedElement
             */
            fun addSharedElement(sharedElement: View, name: String): Builder {
                mSharedElements[sharedElement] = name
                return this
            }

            /**
             * Constructs the final [Extras] instance.
             *
             * @return An immutable [Extras] instance.
             */
            fun build(): Extras {
                return Extras(mSharedElements)
            }
        }
    }

    companion object {
        private val TAG = "FragmentNavigator"
        private val KEY_BACK_STACK_IDS = "androidx-nav-fragment:navigator:backStackIds"
    }
}

