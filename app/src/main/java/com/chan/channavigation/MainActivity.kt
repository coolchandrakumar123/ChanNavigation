package com.chan.channavigation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chan.channavigation.ui.UIManager
import com.chan.channavigation.ui.navigation.Screen
import com.chan.channavigation.ui.navigation.ScreenType
import com.chan.channavigation.ui.screens.UIScreens

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        UIManager.buildScreens(this, supportFragmentManager, R.id.container, "KBNavDrawer", UIScreens.navDrawer)
    }
}