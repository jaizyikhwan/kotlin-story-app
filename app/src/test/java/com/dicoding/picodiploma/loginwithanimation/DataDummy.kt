package com.dicoding.picodiploma.loginwithanimation

import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ListStoryItem

object DataDummy {

    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                photoUrl = "Photo + $i",
                createdAt = "Created $i",
                name = "Name $i",
                description = "Description $i",
                lon = i.toDouble(),
                lat = i.toDouble(),
                id = "$i"
            )
            items.add(story)
        }
        return items
    }

}