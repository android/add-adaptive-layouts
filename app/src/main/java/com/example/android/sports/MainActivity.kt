/*
 * Copyright (c) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.sports

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.window.layout.WindowMetricsCalculator
import com.example.android.sports.components.BottomNavigationBar
import com.example.android.sports.components.NavRail
import com.example.android.sports.components.NavigationDrawer
import com.example.android.sports.data.MenuItem
import com.example.android.sports.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        val navigationMenuItems = listOf(
            MenuItem.Home,
            MenuItem.Favorites,
            MenuItem.Settings
        )

        binding.navigation.setContent {
            MaterialTheme {
                when (rememberWidthSizeClass()) {
                    WidthSizeClass.COMPACT ->
                        BottomNavigationBar(menuItems = navigationMenuItems) { menuItem ->
                            navController.navigate(menuItem.destinationId)
                        }
                    WidthSizeClass.MEDIUM ->
                        NavRail(menuItems = navigationMenuItems) { menuItem ->
                            navController.navigate(menuItem.destinationId)
                        }
                    WidthSizeClass.EXPANDED ->
                        NavigationDrawer(
                            menuItems = navigationMenuItems,
                            modifier = Modifier.width(256.dp)
                        ) { menuItem ->
                            navController.navigate(menuItem.destinationId)
                        }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) ||
            super.onSupportNavigateUp()
    }
}

enum class WidthSizeClass { COMPACT, MEDIUM, EXPANDED }

@Composable
fun Activity.rememberWidthSizeClass(): WidthSizeClass {
    val configuration = LocalConfiguration.current
    val windowMetrics = remember(configuration) {
        WindowMetricsCalculator.getOrCreate()
            .computeCurrentWindowMetrics(this)
    }
    val windowDpSize = with(LocalDensity.current) {
        windowMetrics.bounds.toComposeRect().size.toDpSize()
    }
    return when {
        windowDpSize.width < 600.dp -> WidthSizeClass.COMPACT
        windowDpSize.width < 840.dp -> WidthSizeClass.MEDIUM
        else -> WidthSizeClass.EXPANDED
    }
}
