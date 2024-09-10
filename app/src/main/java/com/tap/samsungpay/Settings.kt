/*
 *  Created by AhlaamK on 10/17/23, 12:29 PM
 *  Copyright (C) 2023 Tap Payments - All Rights Reserved
 */

package com.tap.samsungpay

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

import androidx.preference.Preference

import android.widget.FrameLayout
import com.chillibits.simplesettings.core.SimpleSettings
import com.chillibits.simplesettings.core.SimpleSettingsConfig
import com.tap.samsungpay.open.TapConfiguration
import com.tap.tapsamsungpay.R

class Settings : AppCompatActivity() , SimpleSettingsConfig.PreferenceCallback  ,SimpleSettingsConfig.OptionsItemSelectedCallback{
    lateinit var settings:FrameLayout
    lateinit var tapConfiguration: TapConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        settings = findViewById(R.id.settings)
        val configuration = SimpleSettingsConfig.Builder()
            .setActivityTitle("Configuration")
            .setPreferenceCallback(this)
            .displayHomeAsUpEnabled(false).setOptionsMenu(R.menu.menu_main,this)
            .build()
        SimpleSettings(this, configuration).show(R.xml.preferences)

    }

    override fun onPreferenceClick(context: Context, key: String): Preference.OnPreferenceClickListener? {
        return when(key) {
            "dialog_preference" ->Preference.OnPreferenceClickListener {
                val intent = Intent(this@Settings, MainActivity::class.java)
                finish()
                startActivity(intent)

                true
            }

            else -> super.onPreferenceClick(context, key)
        }

    }


    override fun onSettingsOptionsItemSelected(itemId: Int) {
        if(itemId == R.id.actionDone){
            val intent = Intent(this@Settings, MainActivity::class.java)
            finish()
            startActivity(intent)
        }

    }


}