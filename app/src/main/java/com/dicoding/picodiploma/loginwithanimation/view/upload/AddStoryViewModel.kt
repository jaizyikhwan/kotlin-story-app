package com.dicoding.picodiploma.loginwithanimation.view.upload

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dicoding.picodiploma.loginwithanimation.data.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject
import com.dicoding.picodiploma.loginwithanimation.data.Result

@HiltViewModel
class AddStoryViewModel @Inject constructor(
    private val repository: StoryRepository
) : ViewModel() {

    fun uploadStory(file: File, description: String) = liveData {
        emit(Result.Loading) // Emit loading state
        try {
            emitSource(repository.uploadStory(file, description)) // Panggil repository tanpa token
        } catch (e: Exception) {
            emit(Result.Error("Gagal mengunggah cerita: ${e.message}"))
        }
    }

}