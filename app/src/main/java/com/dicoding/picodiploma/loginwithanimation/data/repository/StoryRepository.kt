package com.dicoding.picodiploma.loginwithanimation.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.picodiploma.loginwithanimation.data.Result
import com.dicoding.picodiploma.loginwithanimation.data.StoryPagingSource
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ErrorResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.UploadStoryResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.retrofit.ApiService
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

class StoryRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun getStoriesPaging(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                Log.d("PagingDebug", "Initializing PagingSource")
                StoryPagingSource(apiService)
            }
        ).liveData.also {
            Log.d("PagingDebug", "LiveData created for PagingData")
        }
    }

    fun getStoriesWithLocation(): LiveData<Result<List<ListStoryItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getStoriesWithLocation()
            emit(Result.Success(response.listStory))
        } catch (e: HttpException) {
            val jsonString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
            emit(Result.Error(errorBody.message ?: "Terjadi kesalahan"))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Error, something went wrong"))
        }
    }

    fun getStories(): LiveData<Result<List<ListStoryItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getStories()
            emit(Result.Success(response.listStory))
        } catch (e: HttpException) {
            val jsonString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
            emit(Result.Error(errorBody.message ?: "Terjadi kesalahan"))
        }
    }

    fun uploadStory(file: File, description: String): LiveData<Result<UploadStoryResponse>> =
        liveData {
            emit(Result.Loading)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val requestBody = description.toRequestBody("text/plain".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )
            try {
                val successResponse = apiService.uploadStory(multipartBody, requestBody)
                emit(Result.Success(successResponse))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, UploadStoryResponse::class.java)
                emit(Result.Error(errorResponse.message))
            }
        }
}