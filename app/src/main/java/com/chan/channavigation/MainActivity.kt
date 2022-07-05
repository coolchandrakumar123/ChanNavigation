package com.chan.channavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chan.channavigation.ui.UIManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        UIManager.buildScreens(supportFragmentManager, R.id.container, UIManager.allScreenList.first().screenName)
    }
}