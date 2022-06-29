package com.chan.channavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.chan.channavigation.ui.main.MainFragment
import com.chan.channavigation.ui.main.ToolbarFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            /*supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()*/
            prepareNavigation()
        }
    }

    private fun prepareNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController //findNavController(R.id.nav_host_fragment)
        navController.graph = navController.createGraph(
            startDestination = "mainFragment"
        ) {
            fragment<MainFragment>("mainFragment") {
                label = "MainFragment"
            }

            fragment<ToolbarFragment>("toolbarFragment") {
                label = "ToolbarFragment"
                /*argument(nav_arguments.plant_id) {
                    type = NavType.StringType
                }*/
            }
        }
    }

    /*override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }*/
}