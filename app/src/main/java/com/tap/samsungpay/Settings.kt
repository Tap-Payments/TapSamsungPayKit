/*
 *  Created by AhlaamK on 10/17/23, 12:29 PM
 *  Copyright (C) 2023 Tap Payments - All Rights Reserved
 */

package com.tap.samsungpay

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import androidx.preference.Preference
import android.os.Bundle
import android.widget.FrameLayout
import com.chillibits.simplesettings.core.SimpleSettings
import com.chillibits.simplesettings.core.SimpleSettingsConfig

class Settings : AppCompatActivity() , SimpleSettingsConfig.PreferenceCallback {
    lateinit var settings:FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        settings = findViewById(R.id.settings)
        val configuration = SimpleSettingsConfig.Builder()
            .setActivityTitle("Configuration")
            .setPreferenceCallback(this)
            .displayHomeAsUpEnabled(false)
            .build()
        SimpleSettings(this, configuration).show(R.xml.preferences)

    }

    override fun onPreferenceClick(context: Context, key: String): Preference.OnPreferenceClickListener? {
        return when(key) {
            "dialog_preference" ->Preference.OnPreferenceClickListener {
                val intent = Intent(this@Settings, MainActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onPreferenceClick(context, key)
        }
    }




}