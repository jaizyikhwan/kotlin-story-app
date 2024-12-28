package com.dicoding.picodiploma.loginwithanimation.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.picodiploma.loginwithanimation.data.repository.AuthRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.dicoding.picodiploma.loginwithanimation.data.Result

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val storyRepository: StoryRepository,
) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return authRepository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    fun getStories(): LiveData<Result<List<ListStoryItem>>> = liveData {
        emit(Result.Loading)
        Log.d("MainViewModel", "Fetching stories...")
        try {
            emitSource(storyRepository.getStories())
            Log.d("MainViewModel", "Stories fetched successfully.")
        } catch (e: Exception) {
            Log.e("MainViewModel", "Error fetching stories: ${e.message}")
            emit(Result.Error("Gagal mendapatkan cerita: ${e.message}"))
        }
    }

    val pagedStories: LiveData<PagingData<ListStoryItem>> = liveData {
        Log.d("MainViewModel", "Initializing paging stories...")
        emitSource(storyRepository.getStoriesPaging().cachedIn(viewModelScope))
        Log.d("MainViewModel", "Paging stories initialized.")
    }


}