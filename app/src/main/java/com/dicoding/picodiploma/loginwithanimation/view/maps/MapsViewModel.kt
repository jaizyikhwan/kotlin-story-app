package com.dicoding.picodiploma.loginwithanimation.view.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.dicoding.picodiploma.loginwithanimation.data.Result
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val repository: StoryRepository,
) : ViewModel() {

    fun getStoriesWithLocation(): LiveData<Result<List<ListStoryItem>>> = liveData {
        emit(Result.Loading) // Emit loading state
        try {
            emitSource(repository.getStoriesWithLocation()) // Emit hasil dari StoryRepository
        } catch (e: Exception) {
            emit(Result.Error("Gagal mendapatkan cerita: ${e.message}"))
        }
    }

}
