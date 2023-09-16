package com.example.fetchrewardscode

import androidx.test.core.app.ApplicationProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock

@RunWith(AndroidJUnit4::class)
class ItemAdapterTest {

    private lateinit var adapter: ItemAdapter

    @Mock
    private lateinit var mockViewHolder: ItemAdapter.CustomViewHolder
    @Before
    fun setup() {
        // Prepare test data
        val itemsMap = mapOf(
            1 to listOf("Name1", "Name2"),
            2 to listOf("Name3"),
            3 to listOf("Name4", "Name5", "Name6")
        )

        // Initialize the adapter with test data
        adapter = ItemAdapter(itemsMap)
    }

    @Test
    fun getItemCount() {
        // Ensure that getItemCount returns the correct number of items
        assertEquals(3, adapter.itemCount)
    }
}
