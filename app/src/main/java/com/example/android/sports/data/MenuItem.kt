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

package com.example.android.sports.data

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.example.android.sports.R

sealed class MenuItem(
    @DrawableRes val iconId: Int,
    @StringRes val labelId: Int,
    @IdRes val destinationId: Int
) {

    object Home : MenuItem(
        R.drawable.ic_baseline_home_24,
        R.string.home,
        R.id.SportsListFragment
    )

    object Favorites : MenuItem(
        R.drawable.ic_baseline_favorite_24,
        R.string.favorites,
        R.id.FavoritesFragment
    )

    object Settings : MenuItem(
        R.drawable.ic_baseline_settings_24,
        R.string.settings,
        R.id.SettingsFragment
    )
}
