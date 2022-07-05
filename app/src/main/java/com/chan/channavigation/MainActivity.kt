package com.chan.channavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chan.channavigation.ui.UIManager
import com.chan.channavigation.ui.navigation.Screen
import com.chan.channavigation.ui.navigation.ScreenType

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        UIManager.buildScreens(supportFragmentManager, R.id.container, "KBNavDrawer", screenList = arrayListOf(
            Screen(
            screenId = 1011,
            screenType = ScreenType.NAV_DRAWER,
            screenName = "KBNavDrawer",
            screenGroup = "KB",
            navigation = "KBCategory"
        )
        ))
    }
}