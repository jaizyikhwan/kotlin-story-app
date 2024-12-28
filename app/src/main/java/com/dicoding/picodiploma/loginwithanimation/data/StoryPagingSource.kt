package com.dicoding.picodiploma.loginwithanimation.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.remote.retrofit.ApiService

class StoryPagingSource(
    private val apiService: ApiService,
) : PagingSource<Int, ListStoryItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val pageSize = params.loadSize

            Log.d("PagingDebug", "Requesting page: $position with size: $pageSize")

            val responseData = apiService.getStories(position, pageSize).listStory

            Log.d("PagingDebug", "API response: ${responseData.size} stories loaded for page $position")

            val prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1
            val nextKey = if (responseData.isEmpty()) null else position + 1

            Log.d("PagingDebug", "Next key calculated as: $nextKey, Prev key: $prevKey")

            LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}