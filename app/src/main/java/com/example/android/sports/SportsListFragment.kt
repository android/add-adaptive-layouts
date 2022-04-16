/*
 * Copyright (c) 2021 The Android Open Source Project
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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.example.android.sports.databinding.FragmentSportsListBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

class SportsListFragment : Fragment() {

    private val sportsViewModel: SportsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentSportsListBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSportsListBinding.bind(view)
        val slidingPaneLayout = binding.slidingPaneLayout
        slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED

        // Connect the SlidingPaneLayout to the system back button.
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            SportsListOnBackPressedCallback(slidingPaneLayout)
        )

        // Initialize the adapter and set it to the RecyclerView.
        val adapter = SportsAdapter {
            // Update the user selected sport as the current sport in the shared viewmodel
            sportsViewModel.updateCurrentSport(it)
            if (slidingPaneLayout.isSlideable && !slidingPaneLayout.isOpen) {
                // Navigate to the details screen
                val action = SportsListFragmentDirections.actionSportsListFragmentToNewsFragment()
                this.findNavController().navigate(action)
            }
        }
        binding.recyclerView.adapter = adapter
        adapter.submitList(sportsViewModel.sportsData)
    }
}

class SportsListOnBackPressedCallback(
    private val slidingPaneLayout: SlidingPaneLayout
) : OnBackPressedCallback(slidingPaneLayout.isSlideable && slidingPaneLayout.isOpen),
    SlidingPaneLayout.PanelSlideListener {

    init {
        slidingPaneLayout.addPanelSlideListener(this)
    }
    /**
     * Callback for handling the [OnBackPressedDispatcher.onBackPressed] event.
     */
    override fun handleOnBackPressed() {
        slidingPaneLayout.closePane()
    }

    /**
     * Called when a detail view's position changes.
     *
     * @param panel       The child view that was moved
     * @param slideOffset The new offset of this sliding pane within its range, from 0-1
     */
    override fun onPanelSlide(panel: View, slideOffset: Float) {
    }

    /**
     * Called when a detail view becomes slid completely open.
     *
     * @param panel The detail view that was slid to an open position
     */
    override fun onPanelOpened(panel: View) {
        isEnabled = true
    }

    /**
     * Called when a detail view becomes slid completely closed.
     *
     * @param panel The detail view that was slid to a closed position
     */
    override fun onPanelClosed(panel: View) {
        isEnabled = false
    }
}
