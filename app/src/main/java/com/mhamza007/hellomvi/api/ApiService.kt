package com.mhamza007.hellomvi.api

import androidx.lifecycle.LiveData
import com.mhamza007.hellomvi.model.BlogPost
import com.mhamza007.hellomvi.model.User
import com.mhamza007.hellomvi.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("placeholder/user/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ): LiveData<GenericApiResponse<User>>

    @GET("placeholder/blogs")
    fun getBlogPosts(): LiveData<GenericApiResponse<List<BlogPost>>>
}