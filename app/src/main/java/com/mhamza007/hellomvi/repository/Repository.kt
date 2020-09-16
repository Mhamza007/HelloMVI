package com.mhamza007.hellomvi.repository

import androidx.lifecycle.LiveData
import com.mhamza007.hellomvi.api.RetrofitBuilder
import com.mhamza007.hellomvi.model.BlogPost
import com.mhamza007.hellomvi.model.User
import com.mhamza007.hellomvi.ui.main.state.MainViewState
import com.mhamza007.hellomvi.util.ApiSuccessResponse
import com.mhamza007.hellomvi.util.DataState
import com.mhamza007.hellomvi.util.GenericApiResponse

object Repository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<List<BlogPost>, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                result.value = DataState.data(
                    data = MainViewState(
                        blogPosts = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                return RetrofitBuilder.apiService.getBlogPosts()
            }
        }.asLiveData()
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<User, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(
                    data = MainViewState(
                        user = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return RetrofitBuilder.apiService.getUser(userId)
            }
        }.asLiveData()
    }
}