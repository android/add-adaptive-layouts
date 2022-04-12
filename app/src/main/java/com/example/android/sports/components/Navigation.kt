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

package com.example.android.sports.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.android.sports.data.MenuItem

@Composable
fun BottomNavigationBar(
    menuItems: List<MenuItem>,
    modifier: Modifier = Modifier,
    onMenuSelected: (MenuItem) -> Unit = {}
) {

    NavigationBar(modifier = modifier) {
        menuItems.forEach { menuItem ->
            NavigationBarItem(
                selected = false,
                onClick = { onMenuSelected(menuItem) },
                icon = {
                    Icon(
                        painter = painterResource(id = menuItem.iconId),
                        contentDescription = null
                    )
                },
                label = { Text(text = stringResource(id = menuItem.labelId)) }
            )
        }
    }
}

@Composable
fun NavRail(
    menuItems: List<MenuItem>,
    modifier: Modifier = Modifier,
    onMenuSelected: (MenuItem) -> Unit = {}
) {
    NavigationRail(modifier = modifier) {
        menuItems.forEach { menuItem ->
            NavigationRailItem(
                selected = false,
                onClick = { onMenuSelected(menuItem) },
                icon = {
                    Icon(
                        painter = painterResource(id = menuItem.iconId),
                        contentDescription = null
                    )
                },
                label = { Text(text = stringResource(id = menuItem.labelId)) }
            )
        }
    }
}

@Composable
fun NavigationDrawer(
    menuItems: List<MenuItem>,
    modifier: Modifier = Modifier,
    onMenuSelected: (MenuItem) -> Unit = {}
) {
    Column(modifier = modifier) {
        menuItems.forEach { menuItem ->
            Row(
                modifier = Modifier
                    .clickable { onMenuSelected(menuItem) }
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Image(painter = painterResource(id = menuItem.iconId), contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = menuItem.labelId))
            }
        }
    }
}
